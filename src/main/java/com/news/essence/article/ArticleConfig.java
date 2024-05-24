package com.news.essence.article;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Configuration
public class ArticleConfig {

    @Bean
    CommandLineRunner commandLineRunner(ArticleRepository repository){
        return args -> {
            Article article1 = new Article(

                    "Title",
                    "image_url",
                    "Summary with a lot of words and bullet points",
                    "source page link",
                    LocalDateTime.now(),
                    "New York Times"
            );
            Article article2 = new Article(

                    "Title2",
                    "image_url2",
                    "Summary with a lot of words and bullet points2",
                    "source page link2",
                    LocalDateTime.of(2024, Month.MAY, 22,10, 10, 10),
                    "New York Times2"
            );
            repository.saveAll(
                    List.of(article1, article2)
            );
        };
    }
}
