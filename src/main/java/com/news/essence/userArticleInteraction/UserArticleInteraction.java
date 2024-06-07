package com.news.essence.userArticleInteraction;
import com.news.essence.article.Article;
import com.news.essence.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_article_interaction")
public class UserArticleInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private boolean userLikedArticle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public boolean isUserLikedArticle() {
        return userLikedArticle;
    }

    public void setUserLikedArticle(boolean userLikedArticle) {
        this.userLikedArticle = userLikedArticle;
    }
}
