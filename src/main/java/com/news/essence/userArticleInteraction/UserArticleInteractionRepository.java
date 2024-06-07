package com.news.essence.userArticleInteraction;

import com.news.essence.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserArticleInteractionRepository extends JpaRepository<UserArticleInteraction, Long> {
    List<UserArticleInteraction> findByUser(User user);
}
