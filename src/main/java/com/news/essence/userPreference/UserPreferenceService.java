package com.news.essence.userPreference;

import com.news.essence.article.Article;
import com.news.essence.category.Category;
import com.news.essence.category.CategoryRepository;
import com.news.essence.article.ArticleRepository;
import com.news.essence.user.User;
import com.news.essence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserPreferenceService {

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public UserPreference findUserPreference(Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        return userPreferenceRepository.findByUserAndCategory(user, category);
    }

    public List<UserPreference> findUserPreferences(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return userPreferenceRepository.findByUser(user);
    }

    @Transactional
    public void updateUserPreference(Long userId, Long articleId, boolean userLikedArticle) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));

        for (Category category : article.getCategories()) {
            UserPreference userPreference = userPreferenceRepository.findByUserAndCategory(user, category);
            if (userPreference == null) {
                userPreference = new UserPreference();
                userPreference.setUser(user);
                userPreference.setCategory(category);
                userPreference.setPreferenceScore(0.0f);
            }

            if (userLikedArticle) {
                userPreference.setPreferenceScore(userPreference.getPreferenceScore() + 0.5f);
            } else {
                userPreference.setPreferenceScore(userPreference.getPreferenceScore() - 0.5f);
            }

            userPreferenceRepository.save(userPreference);
        }
    }
}
