package com.news.essence.userReadArticles;

import com.news.essence.article.Article;
import com.news.essence.article.ArticleRepository;
import com.news.essence.user.User;
import com.news.essence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserReadArticlesService {

    @Autowired
    private UserReadArticlesRepository userReadArticlesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    public void logUserReadArticle(Long userId, Long articleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));

        UserReadArticles userReadArticle = new UserReadArticles();
        userReadArticle.setUser(user);
        userReadArticle.setArticle(article);

        userReadArticlesRepository.save(userReadArticle);
    }

    public List<UserReadArticles> getUserReadArticles(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return userReadArticlesRepository.findByUser(user);
    }
}