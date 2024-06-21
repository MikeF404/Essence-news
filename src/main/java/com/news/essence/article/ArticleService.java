package com.news.essence.article;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.news.essence.OpenAIClient;
import com.news.essence.category.Category;
import com.news.essence.category.CategoryDto;
import com.news.essence.category.CategoryRepository;
import com.news.essence.user.UserService;
import com.news.essence.userPreference.UserPreference;
import com.news.essence.userPreference.UserPreferenceService;
import com.news.essence.userReadArticles.UserReadArticles;
import com.news.essence.userReadArticles.UserReadArticlesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.news.essence.util.CategoryUtils.simplifyCategory;

@Service
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserPreferenceService userPreferenceService;
    @Autowired
    private UserReadArticlesRepository userReadArticlesRepository;
    private static final int PAGE_SIZE = 20;
    private final OpenAIClient openAIClient;
    @Autowired
    private UserService userService;
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

        Map<String, Object> queryInner = new HashMap<>();
        queryInner.put("dateStart", previousDate.format(formatter));
        queryInner.put("dateEnd", currentDate.format(formatter));
        queryInner.put("lang", "eng");

        // Doesn't really work for some reason
        queryInner.put("ignoreSourceUri", List.of(
                "exbulletin.com",
                "cepr.org",
                "www.tradingview.com",
                "www.newsdrum.in",
                "it.marketscreener.com",
                "agenceurope.eu",
                "bankingtimes.co.uk",
                "ndtvprofit.com",
                "www.esdelatino.com",
                "www.czso.cz"
        ));

        Map<String, Object> query = new HashMap<>();
        query.put("$query", queryInner);

        Map<String, Object> filter = new HashMap<>();
        filter.put("startSourceRankPercentile", 90);
        filter.put("endSourceRankPercentile", 100);
        filter.put("isDuplicate", "skipDuplicates");

        query.put("$filter", filter);

        Map<String, Object> payload = new HashMap<>();
        payload.put("action", "getArticles");
        payload.put("query", query);
        payload.put("resultType", "articles");
        payload.put("articlesSortBy", "date");
        payload.put("includeArticleSocialScore", true);
        payload.put("includeArticleCategories", true);
        payload.put("includeArticleImage", true);
        payload.put("includeArticleOriginalArticle", true);
        payload.put("includeSourceTitle", false);
        payload.put("apiKey", env.getProperty("newsapi.key"));
        return payload;
    }



    @Transactional
    public Page<Article> getRecentArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> recentArticles = articleRepository.findRecentArticles(pageable);

        LocalDateTime lastUpdate = recentArticles != null && recentArticles.hasContent()
                ? recentArticles.getContent().get(0).getDateTimePub()
                : LocalDateTime.MIN;

        if (ChronoUnit.HOURS.between(lastUpdate, LocalDateTime.now()) > 12) {
            fetchAndStoreArticles();
            recentArticles = articleRepository.findRecentArticles(pageable);
        }

        return recentArticles;
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

        /*
        logger.info("Payload: {}", payload);
        logger.info("API Response: {}", response.getBody().toString());
        logger.info("Saving articles: {}", articles);
         */
        articleRepository.saveAll(articles);
    }



    private Article mapToEntity(ArticleDTO articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setUrl(articleDto.getUrl());
        article.setImage(articleDto.getImage());
        article.setSummary(articleDto.getSummary());
        article.setDateTimePub(articleDto.getDateTimePub());
        article.setUri(articleDto.getUri());
        article.setBody(articleDto.getBody());

        Set<Category> categories = new HashSet<>();
        for (CategoryDto categoryDto : articleDto.getCategories()) {
            String simplifiedCategoryName = simplifyCategory(categoryDto.getUri());
            Category category = categoryRepository.findByName(simplifiedCategoryName);
            if (category == null) {
                category = new Category();
                category.setName(simplifiedCategoryName);
                category.setParentName(simplifiedCategoryName.split("/")[0]);
                categoryRepository.save(category);
            }
            categories.add(category);
        }
        article.setCategories(categories);

        return article;
    }

    @Transactional
    public List<ArticleDTO> getRelevantArticles(Long userId, int page) {
        // Fetch user preferences
        List<UserPreference> preferences = userPreferenceService.getUserPreferences(userId);
        Map<Category, Double> userPreferences = preferences.stream()
                .collect(Collectors.toMap(UserPreference::getCategory, UserPreference::getPreferenceScore));

        // Fetch articles read by user
        List<UserReadArticles> readArticles = userReadArticlesRepository.findByUserId(userId);
        Set<Long> readArticleIds = readArticles.stream().map(UserReadArticles::getArticleUri).collect(Collectors.toSet());

        // Fetch all articles and filter out read ones
        List<Article> allArticles = articleRepository.findAll();
        List<Article> filteredArticles = allArticles.stream()
                .filter(article -> !readArticleIds.contains(article.getUri()))
                .toList();

        // Sort articles by relevance
        List<Article> relevantArticles = filteredArticles.stream()
                .sorted((a, b) -> Double.compare(computeRelevance(b, userPreferences), computeRelevance(a, userPreferences)))
                .skip((long) page * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();

        List<ArticleDTO> list = relevantArticles.stream().map(ArticleDTO::new).collect(Collectors.toList());

        return list;
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
                // Exclude unwanted sources after fetching the data
                Set<String> blacklistedUrls = Set.of(
                        "exbulletin.com",
                        "cepr.org",
                        "www.tradingview.com",
                        "www.newsdrum.in",
                        "it.marketscreener.com",
                        "agenceurope.eu",
                        "bankingtimes.co.uk",
                        "ndtvprofit.com",
                        "www.esdelatino.com",
                        "www.czso.cz",
                        "farmersweekly.co.za",
                        "investegate.co.uk"
                );

                List<Article> list = new ArrayList<>();
                for (ArticleDTO articleDto : response.getArticles().getResults()) {
                    if (!articleRepository.existsById(articleDto.getUri())) {
                        if (blacklistedUrls.stream().noneMatch(articleDto.getUrl()::contains)) {
                            Article article = mapToEntity(articleDto);
                            list.add(article);
                        }
                    }
                }
                return list;
            } else {
                logger.error("No articles found in the JSON data.");
                return List.of();
            }
        } catch (Exception e) {
            logger.error("Error processing JSON data", e);
            return List.of();
        }
    }

    private double computeRelevance(Article article, Map<Category, Double> userPreferences) {
        return article.getCategories().stream()
                .mapToDouble(category -> userPreferences.getOrDefault(category, 0.0))
                .sum();
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
                article.setBody(null); // we don't need the body anymore - so we remove it to save space
                article.setViewCount(article.getViewCount()+1);
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
