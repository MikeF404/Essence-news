package com.news.essence.user;

import com.news.essence.userArticleInteraction.UserArticleInteraction;
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
    private Set<UserPreference> preferences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserArticleInteraction> interactions;

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

    public Set<UserArticleInteraction> getInteractions() {
        return interactions;
    }

    public void setInteractions(Set<UserArticleInteraction> interactions) {
        this.interactions = interactions;
    }
}
