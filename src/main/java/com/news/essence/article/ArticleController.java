package com.news.essence.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getSummary(@PathVariable Long uri){

        return articleService.getArticleSummary(uri);
    }
}
