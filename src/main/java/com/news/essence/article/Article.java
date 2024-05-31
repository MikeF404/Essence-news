package com.news.essence.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.news.essence.util.LongDeserializer;
import com.news.essence.util.UriConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    @Id
    @JsonDeserialize(using = LongDeserializer.class)
    private long uri;
    private LocalDateTime dateTimePub;
    private String url;
    @Lob
    private String title;
    @Lob
    private String body; // body will be way larger than standard 255 chars
    private String author;
    private String sourceName;
    @Lob
    private String image;
    @Lob
    private String summary;

    public Article(long uri, LocalDateTime dateTimePub, String url, String title, String body, String author, String sourceName, String image) {
        this.uri = uri;
        this.dateTimePub = dateTimePub;
        this.url = url;
        this.title = title;
        this.body = body;
        this.author = author;
        this.sourceName = sourceName;
        this.image = image;
    }
    public Article() {
    }

    @Override
    public String toString() {
        return "Article{" +
                "uri=" + uri +
                ", dateTimePub=" + dateTimePub +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", author='" + author + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", image='" + image + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    public long getUri() {
        return uri;
    }

    public void setUri(long uri) {
        this.uri = uri;
    }

    public LocalDateTime getDateTimePub() {
        return dateTimePub;
    }

    public void setDateTimePub(LocalDateTime dateTimePub) {
        this.dateTimePub = dateTimePub;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //if the uri contains a digit and cannot be converted to Long directly
    public void setUri(String uri) {
        this.uri = UriConverter.convertStringToLong(uri);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
