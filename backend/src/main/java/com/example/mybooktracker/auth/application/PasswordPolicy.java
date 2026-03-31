package com.example.mybooktracker.auth.application;

import java.util.regex.Pattern;

public final class PasswordPolicy {

    public static final int MIN_LENGTH = 12;
    public static final int MAX_LENGTH = 72;
    public static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{12,72}$";
    public static final String VALIDATION_MESSAGE =
            "must be 12-72 characters long and include uppercase, lowercase, and a number";
    public static final String USER_FACING_MESSAGE =
            "Password must be 12-72 characters long and include uppercase, lowercase, and a number";

    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private PasswordPolicy() {
    }

    public static boolean isValid(String password) {
        return password != null && PATTERN.matcher(password).matches();
    }
}
