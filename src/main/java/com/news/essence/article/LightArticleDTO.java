package com.news.essence.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.news.essence.util.LongDeserializer;

import java.time.LocalDateTime;


// Light ArticleDTO is utilized to avoid sending unnecessary information (for example article body
// since the user only sees the summaries) from the Article entity to the user.
public class LightArticleDTO {
    @JsonDeserialize(using = LongDeserializer.class)
    private Long uri;
    private String title;
    private String url;
    private String summary;
    private String image;
    private LocalDateTime dateTimePub;

    public LightArticleDTO(Long uri, String title, String url, String summary, String image, LocalDateTime dateTimePub) {
        this.uri = uri;
        this.title = title;
        this.url = url;
        this.summary = summary;
        this.image = image;
        this.dateTimePub = dateTimePub;
    }

    public LightArticleDTO() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getDateTimePub() {
        return dateTimePub;
    }

    public void setDateTimePub(LocalDateTime dateTimePub) {
        this.dateTimePub = dateTimePub;
    }
}
