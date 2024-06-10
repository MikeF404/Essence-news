package com.news.essence.userReadArticles;

import com.news.essence.article.Article;

import java.time.LocalDateTime;

public class UserReadArticlesDTO {
    private Long uri;
    private String title;
    private LocalDateTime dateTimePub;
    private String author;
    private String sourceName;
    private String image;
    private String summary;

    public UserReadArticlesDTO(Article article) {
        this.uri = article.getUri();
        this.title = article.getTitle();
        this.dateTimePub = article.getDateTimePub();
        this.author = article.getAuthor();
        this.sourceName = article.getSourceName();
        this.image = article.getImage();
        this.summary = article.getSummary();
    }

    public UserReadArticlesDTO(Long uri, String title, LocalDateTime dateTimePub, String author, String sourceName, String image, String summary) {
        this.uri = uri;
        this.title = title;
        this.dateTimePub = dateTimePub;
        this.author = author;
        this.sourceName = sourceName;
        this.image = image;
        this.summary = summary;
    }
    public Long getUri() {
        return uri;
    }




    public void setUri(Long uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTimePub() {
        return dateTimePub;
    }

    public void setDateTimePub(LocalDateTime dateTimePub) {
        this.dateTimePub = dateTimePub;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
