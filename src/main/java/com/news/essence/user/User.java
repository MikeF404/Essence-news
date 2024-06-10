package com.news.essence.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.news.essence.userReadArticles.UserReadArticles;
import com.news.essence.userPreference.UserPreference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "app_user") // since "user" is a reserved keyword
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //to avoid infinite recursion
    private Set<UserPreference> preferences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserReadArticles> readArticles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Set<UserPreference> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<UserPreference> preferences) {
        this.preferences = preferences;
    }

    public Set<UserReadArticles> getReadArticles() {
        return readArticles;
    }

    public void setReadArticles(Set<UserReadArticles> readArticles) {
        this.readArticles = readArticles;
    }
}
