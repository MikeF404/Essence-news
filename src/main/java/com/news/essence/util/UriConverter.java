package com.news.essence.util;

import java.util.Random;

// This class replaces all non-digit characters with random numbers in case uri contains a non-digit char
public class UriConverter {

    private static final Random random = new Random();

    public static long convertStringToLong(String uri) {
        StringBuilder numericUri = new StringBuilder();
        for (char c : uri.toCharArray()) {
            if (Character.isDigit(c)) {
                numericUri.append(c);
            } else {
                numericUri.append(random.nextInt(10));
            }
        }
        return Long.parseLong(numericUri.toString());
    }

}
