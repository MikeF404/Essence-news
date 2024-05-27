package com.news.essence.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private Environment env;
    private final RestTemplate restTemplate = new RestTemplate();
    private String getNewsApiUrl() {
        String apiKey = env.getProperty("newsapi.key");
        String query = URLEncoder.encode("{\"$query\":{\"$and\":[{\"$or\":[{\"categoryUri\":\"dmoz/Science/Technology\"},{\"categoryUri\":\"dmoz/Home/Cooking\"},{\"categoryUri\":\"dmoz/Science/Software\"},{\"categoryUri\":\"dmoz/Society/Law/Courts\"},{\"categoryUri\":\"dmoz/Society/Politics/Lobbying\"},{\"categoryUri\":\"dmoz/Games/Game_Studies\"},{\"categoryUri\":\"dmoz/Games/Video_Games\"},{\"categoryUri\":\"dmoz/Business/E-Commerce\"},{\"categoryUri\":\"dmoz/Health/Fitness/Advice_and_Guides\"}]},{\"locationUri\":\"http://en.wikipedia.org/wiki/United_States\"},{\"dateStart\":\"2024-05-19\",\"dateEnd\":\"2024-05-26\",\"lang\":\"eng\"}]},\"$filter\":{\"startSourceRankPercentile\":90,\"endSourceRankPercentile\":100,\"isDuplicate\":\"skipDuplicates\"}}", StandardCharsets.UTF_8);
        return "https://newsapi.ai/api/v1/article/getArticles?query=" + query + "&resultType=articles&articlesSortBy=date&includeArticleSocialScore=true&includeArticleCategories=true&includeArticleImage=true&includeArticleOriginalArticle=true&includeSourceTitle=false&apiKey=" + apiKey;
    }


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

    public void fetchAndStoreArticles() {
        String url = getNewsApiUrl();
        NewsApiResponse response = restTemplate.getForObject(url, NewsApiResponse.class);
        if (response != null && response.getResults() != null) {
            List<Article> articles = response.getResults().stream()
                    .filter(article -> !articleRepository.existsById(article.getUri()))
                    .collect(Collectors.toList());
            articleRepository.saveAll(articles);
        }
    }

    // A method to fill the DB with articles (can be triggered manually)
    public void fillDBWithArticles() {
        fetchAndStoreArticles();
    }
}
