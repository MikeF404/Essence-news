package com.news.essence.userReadArticles;

import com.news.essence.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserReadArticlesRepository extends JpaRepository<UserReadArticles, Long> {
    List<UserReadArticles> findByUser(User user);
    List<UserReadArticles> findByUserId(long userId);


}
