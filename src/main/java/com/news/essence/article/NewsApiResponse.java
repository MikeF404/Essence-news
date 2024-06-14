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
        private List<Article> results;

        // Getters and Setters
        public List<Article> getResults() {
            return results;
        }

        public void setResults(List<Article> results) {
            this.results = results;
        }
    }
}
