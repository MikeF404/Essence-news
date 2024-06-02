package com.news.essence.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryUtils {

    public static String simplifyCategory(String category) {
        String[] parts = category.split("/");
        if (parts.length > 2) {
            return parts[1] + "/" + parts[2];
        }
        return category;
    }

    public static List<String> simplifyCategories(List<String> categories) {
        return categories.stream()
                .map(CategoryUtils::simplifyCategory)
                .distinct() // Ensure unique categories
                .collect(Collectors.toList());
    }
}
