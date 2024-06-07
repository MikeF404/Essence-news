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
        String longUri = numericUri.toString();

        // In long strings - id is usually last 10-11 symbols, and the beginning is just the date
        // i.e. 12-11-2024-012345678910 - the number will be too large for Long, and we are interested in id only
        if (longUri.length() > 12){
            longUri = longUri.substring(longUri.length()-12);
        }
        return Long.parseLong(longUri);
    }

}
