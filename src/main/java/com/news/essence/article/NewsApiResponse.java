package com.news.essence.article;

import java.util.List;

public class NewsApiResponse {
    private ArticlesContainer articles;

    // Getters and Setters
    public ArticlesContainer getArticles() {
        return articles;
    }

    public void setArticles(ArticlesContainer articles) {
        this.articles = articles;
    }

    public static class ArticlesContainer {
        private List<ArticleDto> results;

        // Getters and Setters
        public List<ArticleDto> getResults() {
            return results;
        }

        public void setResults(List<ArticleDto> results) {
            this.results = results;
        }
    }
}
