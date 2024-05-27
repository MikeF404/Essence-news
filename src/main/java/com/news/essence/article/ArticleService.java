package com.news.essence.article;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

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
        String url = getNewsApiUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = getPostPayload();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        /*
        ResponseEntity<NewsApiResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, NewsApiResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getResults() != null) {
            List<Article> articles = response.getBody().getResults().stream()
                    .filter(article -> !articleRepository.existsById(article.getUri()))
                    .collect(Collectors.toList());
         */
        String jsonData = "{\n" +
                "    \"articles\": {\n" +
                "        \"results\": [\n" +
                "            {\n" +
                "                \"uri\": \"8145556983\",\n" +
                "                \"lang\": \"eng\",\n" +
                "                \"isDuplicate\": false,\n" +
                "                \"date\": \"2024-05-25\",\n" +
                "                \"time\": \"15:31:01\",\n" +
                "                \"dateTime\": \"2024-05-25T15:31:01Z\",\n" +
                "                \"dateTimePub\": \"2024-05-25T15:29:52Z\",\n" +
                "                \"dataType\": \"news\",\n" +
                "                \"sim\": 0,\n" +
                "                \"url\": \"https://www.foxweather.com/weather-news/houston-power-outages-derecho-spotted-from-space\",\n" +
                "                \"title\": \"Houston area's 1 million power outages after 100 mph derecho spotted from space\",\n" +
                "                \"body\": \"HOUSTON -- The scars left after an intense derecho blasted the Houston area on May 16 are visible from space.\\n\\nThe line of thunderstorms tore through the city that evening, blistering Houston and its suburbs with wind gusts estimated up to 100 mph.\\n\\nWHAT IS A DERECHO?\\n\\nShattered glass littered downtown streets as the winds blew out dozens of windows from the city's skyscrapers while trees, power lines, and even 10 large electrical transmission towers were knocked over in the gusts.\\n\\nAbout 1 million electrical customers lost power in southeastern Texas that evening -- about 800,000 of them in the Houston metro area, officials said.\\n\\nThe blackouts were noticeable from satellites over 500 miles above Earth's surface. NASA's Suomi-NPP polar-orbiting satellite snapped before and after photos of the Houston metro area lit up at night, showing just how dark wide swaths of the city were in the storm's immediate aftermath.\\n\\nHOUSTON METRO ROCKED BY 100 MPH DERECHO THAT LEFT 7 DEAD AND OVER 1 MILLION WITHOUT POWER\\n\\nThousands of power crews were brought in to help restore electricity and a vast majority of outages have been repaired. CenterPoint Energy's current Houston-area outage page shows just over 1,600 customers still without power.\",\n" +
                "                \"authors\": [\n" +
                "                    {\n" +
                "                        \"uri\": \"scott_sistek@foxweather.com\",\n" +
                "                        \"name\": \"Scott Sistek\",\n" +
                "                        \"type\": \"author\",\n" +
                "                        \"isAgency\": false\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"categories\": [\n" +
                "                    {\n" +
                "                        \"uri\": \"dmoz/Business/Energy/Renewable\",\n" +
                "                        \"label\": \"dmoz/Business/Energy/Renewable\",\n" +
                "                        \"wgt\": 100\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uri\": \"dmoz/Science/Earth_Sciences/Atmospheric_Sciences\",\n" +
                "                        \"label\": \"dmoz/Science/Earth Sciences/Atmospheric Sciences\",\n" +
                "                        \"wgt\": 100\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uri\": \"dmoz/Science/Technology/Space\",\n" +
                "                        \"label\": \"dmoz/Science/Technology/Space\",\n" +
                "                        \"wgt\": 100\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"uri\": \"dmoz/Business/Aerospace_and_Defense/Space\",\n" +
                "                        \"label\": \"dmoz/Business/Aerospace and Defense/Space\",\n" +
                "                        \"wgt\": 100\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"image\": \"https://static.foxweather.com/www.foxweather.com/content/uploads/2024/05/Houston_power_outages.gif\",\n" +
                "                \"originalArticle\": null,\n" +
                "                \"eventUri\": null,\n" +
                "                \"sentiment\": -0.1137254901960785,\n" +
                "                \"wgt\": 454347061,\n" +
                "                \"relevance\": 26\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

            List<Article> articles = parseJsonData(jsonData);
            articleRepository.saveAll(articles);
        //}
        //logger.info("Response: {}", response.getStatusCode());
        logger.info("Payload: {}", payload);
        //logger.info("API Response: {}", response.getBody().toString());
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


}
