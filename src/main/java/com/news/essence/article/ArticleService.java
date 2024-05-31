package com.news.essence.article;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.news.essence.OpenAIClient;
import com.news.essence.util.UriConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    @Autowired
    private ArticleRepository articleRepository;
    private final OpenAIClient openAIClient;

    @Autowired
    private Environment env;
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    public ArticleService(Environment env) {
        this.openAIClient = new OpenAIClient(env.getProperty("openaiapi.key"));
    }

    private String getNewsApiUrl() {
        return "https://newsapi.ai/api/v1/article/getArticles";
    }

    private Map<String, Object> getPostPayload() {
        LocalDate currentDate = LocalDate.now();
        LocalDate previousDate = currentDate.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Map<String, Object> query = new HashMap<>();
        Map<String, Object> queryInner = new HashMap<>();
        queryInner.put("dateStart", previousDate.format(formatter));
        queryInner.put("dateEnd", currentDate.format(formatter));
        queryInner.put("lang", "eng");

        Map<String, Object> filter = new HashMap<>();
        filter.put("startSourceRankPercentile", 90);
        filter.put("endSourceRankPercentile", 100);
        filter.put("isDuplicate", "skipDuplicates");

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("$query", queryInner);
        queryMap.put("$filter", filter);

        query.put("action", "getArticles");
        query.put("query", queryMap);
        query.put("resultType", "articles");
        query.put("articlesSortBy", "date");
        query.put("includeArticleSocialScore", true);
        query.put("includeArticleCategories", true);
        query.put("includeArticleImage", true);
        query.put("includeArticleOriginalArticle", true);
        query.put("includeSourceTitle", false);
        query.put("apiKey", env.getProperty("newsapi.key"));
       return query;
    }

    @Transactional
    public List<Article> getPopularArticles() {
        // Check the last update time
        Article latestArticle = articleRepository.findTopByOrderByDateTimePubDesc();
        LocalDateTime lastUpdate = latestArticle != null ? latestArticle.getDateTimePub() : LocalDateTime.MIN;

        // If the last update was more than 24 hours ago, fetch fresh articles
        if (lastUpdate.isBefore(LocalDateTime.now().minusHours(24))) {
            fetchAndStoreArticles();
        }

        return articleRepository.findAll();
    }

    @Transactional
    public void fetchAndStoreArticles() {
        List<Article> articles;
        String url = getNewsApiUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = getPostPayload();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

            articles = parseJsonData(response.getBody());
        } else {
            logger.error("Failed to fetch articles. Status Code: {}, Response Body: {}", response.getStatusCode(), response.getBody());
            return;  // Exit if the API call failed
        }
        logger.info("Payload: {}", payload);
        /*

        logger.info("API Response: {}", response.getBody().toString());
        logger.info("Saving articles: {}", articles);
         */
        articleRepository.saveAll(articles);
    }

    // A method to fill the DB with articles (can be triggered manually)
    public void fillDBWithArticles() {
        fetchAndStoreArticles();
    }
    private List<Article> parseJsonData(String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

            NewsApiResponse response = objectMapper.readValue(jsonData, NewsApiResponse.class);
            if (response != null && response.getArticles() != null) {
                return response.getArticles().getResults().stream()
                        .filter(article -> !articleRepository.existsById(article.getUri()))
                        .collect(Collectors.toList());
            } else {
                logger.error("No articles found in the JSON data.");
                return List.of();
            }
        } catch (Exception e) {
            logger.error("Error processing JSON data", e);
            return List.of();
        }
    }


    @Transactional
    public String getArticleSummary(Long uri){
        String output ="No article with such uri!";
        Optional<Article> optionalArticle = articleRepository.findById(uri);
        if(optionalArticle.isPresent()){
            Article article = optionalArticle.get();
            if (article.getSummary() == null){
                String summary = summarizeArticle(article.getBody());
                article.setSummary(summary);
                articleRepository.save(article);
                output = summary;
            }
        }
        return output;
    }

    private String summarizeArticle(String content) {
        try {
            return openAIClient.summarize(content);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }
}
