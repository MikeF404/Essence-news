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

    @Transactional
    public void updateUserPreference(User user, Article article, boolean userLikedArticle) {
        for (Category category : article.getCategories()) {
            UserPreference userPreference = userPreferenceRepository.findByUserAndCategory(user, category);
            if (userPreference == null) {
                userPreference = new UserPreference();
                userPreference.setUser(user);
                userPreference.setCategory(category);
            }
            if (userLikedArticle) {
                userPreference.setPreferenceScore(userPreference.getPreferenceScore() + 0.5f);
            } else {
                userPreference.setPreferenceScore(userPreference.getPreferenceScore() - 0.5f);
            }
            userPreferenceRepository.save(userPreference);
        }
    }

    // if user read the article without providing a response
    @Transactional
    public void updateUserPreference(User user, Article article) {
        for (Category category : article.getCategories()) {
            UserPreference userPreference = userPreferenceRepository.findByUserAndCategory(user, category);
            if (userPreference == null) {
                userPreference = new UserPreference();
                userPreference.setUser(user);
                userPreference.setCategory(category);
            }
            userPreference.setPreferenceScore(userPreference.getPreferenceScore() + 0.2f);
            userPreferenceRepository.save(userPreference);
        }
    }

    @Transactional
    public void logUserInteraction(User user, Article article, boolean userLikedArticle) {
        UserReadArticles interaction = new UserReadArticles();
        interaction.setUser(user);
        interaction.setArticle(article);
        //interaction.setUserLikedArticle(userLikedArticle);
        userArticleInteractionRepository.save(interaction);
        updateUserPreference(user, article, userLikedArticle);
    }
}

