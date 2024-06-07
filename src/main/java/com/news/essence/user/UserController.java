package com.news.essence.user;

import com.news.essence.article.Article;
import com.news.essence.userReadArticles.UserReadArticles;
import com.news.essence.userReadArticles.UserReadArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserReadArticlesService userReadArticlesService;

    @PostMapping("/create")
    public Long createNewUser() {
        return userService.createNewUser();
    }
    @PostMapping("/{id}/read-articles")
    public List<UserReadArticles> getUserReadArticles(@PathVariable Long userId) {
        return userReadArticlesService.getUserReadArticles(userId);
    }



}

