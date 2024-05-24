package com.news.essence.article;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Article {
    @Id
    @SequenceGenerator(
            name = "article_sequence",
            sequenceName = "article_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "article_sequence"
    )

    private Long article_id;
    private String title;
    private String image_url;
    private String summary;
    private String src_link;
    private LocalDateTime publish_date;
    private String publisher_name;

    public Article() {
    }

    public Article(Long article_id, String title, String image_url, String summary, String src_link, LocalDateTime publish_date, String publisher_name) {
        this.article_id = article_id;
        this.title = title;
        this.image_url = image_url;
        this.summary = summary;
        this.src_link = src_link;
        this.publish_date = publish_date;
        this.publisher_name = publisher_name;
    }
    public Article(String title, String image_url, String summary, String src_link, LocalDateTime publish_date, String publisher_name) {
        this.title = title;
        this.image_url = image_url;
        this.summary = summary;
        this.src_link = src_link;
        this.publish_date = publish_date;
        this.publisher_name = publisher_name;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSrc_link() {
        return src_link;
    }

    public void setSrc_link(String src_link) {
        this.src_link = src_link;
    }

    public LocalDateTime getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(LocalDateTime publish_date) {
        this.publish_date = publish_date;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    @Override
    public String toString() {
        return "Article{" +
                "article_id=" + article_id +
                ", title='" + title + '\'' +
                ", image_url='" + image_url + '\'' +
                ", summary='" + summary + '\'' +
                ", src_link='" + src_link + '\'' +
                ", publish_date=" + publish_date +
                ", publisher_name='" + publisher_name + '\'' +
                '}';
    }
}
