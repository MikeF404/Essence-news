package com.news.essence.user;

import com.news.essence.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Long createNewUser() {
        return userService.createNewUser();
    }
    @PostMapping("/interact")
    public void logInteraction(@RequestBody InteractionRequest request) {
        User user = userService.findUserById(request.getUserId());
        Article article = userService.findArticleById(request.getArticleId());
        userService.logUserInteraction(user, article, request.isUserLikedArticle());
    }

    public static class InteractionRequest {
        private Long userId;
        private Long articleId;
        private boolean userLikedArticle;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getArticleId() {
            return articleId;
        }

        public void setArticleId(Long articleId) {
            this.articleId = articleId;
        }

        public boolean isUserLikedArticle() {
            return userLikedArticle;
        }

        public void setUserLikedArticle(boolean userLikedArticle) {
            this.userLikedArticle = userLikedArticle;
        }
    }

}

