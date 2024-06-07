package com.news.essence.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/popular")
    public List<Article> getPopularArticles() {
        return articleService.getPopularArticles();
    }

    @GetMapping("/fill")
    public String fillDBWithArticles() {
        articleService.fillDBWithArticles();
        return "Database filled with articles";
    }

    @GetMapping("/summary/{uri}")
    public String getSummary(@PathVariable Long uri, @RequestHeader(value = "id", required = false) Long id){

        return articleService.getArticleSummary(uri, id);
    }
}
