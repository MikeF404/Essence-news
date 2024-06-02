package com.news.essence.userPreferences;

import com.news.essence.category.Category;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToMany
    @JoinTable(
            name = "user_preferences_category",
            joinColumns = @JoinColumn(name = "user_preferences_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> preferredCategories = new HashSet<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Category> getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(Set<Category> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }
}
