package com.news.essence.article;

import com.news.essence.userPreference.UserPreferenceService;
import com.news.essence.userReadArticles.UserReadArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserPreferenceService userPreferenceService;

    @Autowired
    private UserReadArticlesService userReadArticlesService;

    @GetMapping("/popular")
    public List<Article> getPopularArticles() {
        return articleService.getPopularArticles();
    }

    @GetMapping("/fill")
    public String fillDBWithArticles() {
        articleService.fillDBWithArticles();
        return "Database filled with articles";
    }

    @GetMapping("/summary/{articleId}")
    public String getSummary(@PathVariable Long articleId, @RequestHeader(value = "userId", required = false) Long userId){

        String summary = articleService.getArticleSummary(articleId);

        if (userId != null) {
            userReadArticlesService.logUserReadArticle(userId, articleId);
            userPreferenceService.updateUserPreference(userId, articleId, "read");
        }

        return summary;
    }

    @GetMapping("/relevant/{userId}/{page}")
    public List<ArticleDTO> getRelevantArticles(@PathVariable Long userId, @PathVariable int page) {
        return articleService.getRelevantArticles(userId, page);
    }

    @PostMapping("/interact")
    public void logInteraction(@RequestBody InteractionRequest request) {
        userPreferenceService.updateUserPreference(request.getUserId(), request.getArticleId(), request.getInteractionType());
    }

    // InteractionRequest DTO
    public static class InteractionRequest {
        private Long userId;
        private Long articleId;
        private String interactionType; // "read", "moreLikeThis", "lessLikeThis"

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

        public String getInteractionType() {
            return interactionType;
        }

        public void setInteractionType(String interactionType) {
            this.interactionType = interactionType;
        }
    }
}
