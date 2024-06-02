package com.news.essence.userPreferences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
    UserPreferences findByUserId(Long userId);
}
