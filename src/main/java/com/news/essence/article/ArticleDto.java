package com.news.essence.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.news.essence.category.CategoryDto;
import com.news.essence.util.LongDeserializer;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDto {

    private String title;
    private String url;
    private String summary;
    private String image;
    private LocalDateTime dateTimePub;
    @JsonDeserialize(using = LongDeserializer.class)
    private Long uri;

    private List<CategoryDto> categories;

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


    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
