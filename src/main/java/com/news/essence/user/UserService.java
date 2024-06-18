package com.news.essence.user;

import com.news.essence.article.Article;
import com.news.essence.article.ArticleRepository;
import com.news.essence.category.Category;
import com.news.essence.category.CategoryRepository;
import com.news.essence.userReadArticles.UserReadArticles;
import com.news.essence.userReadArticles.UserReadArticlesRepository;
import com.news.essence.userPreference.UserPreference;
import com.news.essence.userPreference.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private UserReadArticlesRepository userArticleInteractionRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public Long createNewUser() {
        User newUser = new User();
        userRepository.save(newUser);
        return newUser.getId();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));
    }
}

