package com.news.essence.userPreferences;

import com.news.essence.category.Category;
import com.news.essence.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserPreferencesService {

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Map<String, Long> getUserCategoryPreferences(Long userId) {
        UserPreferences preferences = userPreferencesRepository.findByUserId(userId);
        if (preferences == null) {
            return Collections.emptyMap();
        }

        Map<String, Long> categoryCounts = preferences.getPreferredCategories().stream()
                .collect(Collectors.groupingBy(Category::getName, Collectors.counting()));

        return categoryCounts;
    }

    public Map<String, Long> getUserParentCategoryPreferences(Long userId) {
        UserPreferences preferences = userPreferencesRepository.findByUserId(userId);
        if (preferences == null) {
            return Collections.emptyMap();
        }

        Map<String, Long> parentCategoryCounts = preferences.getPreferredCategories().stream()
                .collect(Collectors.groupingBy(Category::getParentName, Collectors.counting()));

        return parentCategoryCounts;
    }
}
