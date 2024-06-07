package com.news.essence.userPreference;

import com.news.essence.category.Category;
import com.news.essence.user.User;
import jakarta.persistence.*;

@Entity
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private float preferenceScore = 0.0f;

    // Getters and Setters
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getPreferenceScore() {
        return preferenceScore;
    }

    public void setPreferenceScore(float preferenceScore) {
        this.preferenceScore = preferenceScore;
    }
}

