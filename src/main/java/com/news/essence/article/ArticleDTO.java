package com.news.essence.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.news.essence.category.CategoryDto;
import com.news.essence.util.LongDeserializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ArticleDTO {

    private String title;
    private String url;
    private String summary;
    private String image;
    private LocalDateTime dateTimePub;
    @JsonDeserialize(using = LongDeserializer.class)
    private Long uri;
    private String body;
    private Set<CategoryDto> categories;

    public ArticleDTO() {

    }

    public ArticleDTO(Article article) {
        this.title = article.getTitle();
        this.url = article.getUrl();
        this.summary = article.getSummary();
        this.image = article.getImage();
        this.dateTimePub = article.getDateTimePub();
        this.uri = article.getUri();
        this.body = article.getBody();
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public LocalDateTime getDateTimePub() {
        return dateTimePub;
    }

    public void setDateTimePub(LocalDateTime dateTimePub) {
        this.dateTimePub = dateTimePub;
    }

    public Long getUri() {
        return uri;
    }

    public void setUri(Long uri) {
        this.uri = uri;
    }


    public Set<CategoryDto> getCategories() {
        return categories;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCategories(Set<CategoryDto> categories) {
        this.categories = categories;
    }
}
