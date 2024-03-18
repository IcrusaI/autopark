package com.crusa.autopark.common;

import java.util.Random;

public class CarNumberGenerator {
    public static final String LETTERS = "ABEKMHOPCTYX";
    public static final String VALID_REGEX = "^[" + LETTERS + "]\\d{3}[" + LETTERS + "]{2}\\d{2,3}$";
    private static final Random RANDOM = new Random();

    public static String generate() {
        String numberPart = String.format("%03d", RANDOM.nextInt(1000));
        String regionPart = String.format("%02d", RANDOM.nextInt(100));

        return String.format("%s%s%s%s%s", generateLetter(), numberPart, generateLetter(), generateLetter(), regionPart);
    }

    private static Character generateLetter() {
        return LETTERS.charAt(RANDOM.nextInt(LETTERS.length()));
    }
}

