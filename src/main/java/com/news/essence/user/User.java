package com.news.essence.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.news.essence.userReadArticles.UserReadArticles;
import com.news.essence.userPreference.UserPreference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user") // since "user" is a reserved keyword
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
