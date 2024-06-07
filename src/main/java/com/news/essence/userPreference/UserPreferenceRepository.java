package com.news.essence.userPreference;

import com.news.essence.category.Category;
import com.news.essence.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    List<UserPreference> findByUser(User user);
    UserPreference findByUserAndCategory(User user, Category category);
}
