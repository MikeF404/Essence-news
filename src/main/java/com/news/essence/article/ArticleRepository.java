package com.news.essence.article;

import com.news.essence.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findTopByOrderByDateTimePubDesc();

}
