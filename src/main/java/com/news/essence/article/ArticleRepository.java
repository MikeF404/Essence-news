package com.news.essence.article;

import com.news.essence.category.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findTopByOrderByDateTimePubDesc();
    @Query("SELECT DISTINCT a FROM Article a ORDER BY a.dateTimePub DESC")
    Page<Article> findRecentArticles(Pageable pageable);
}
