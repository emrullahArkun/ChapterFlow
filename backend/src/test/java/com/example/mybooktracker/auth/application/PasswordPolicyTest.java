package com.example.mybooktracker.auth.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordPolicyTest {

    @Test
    void isValid_ShouldReturnFalse_WhenPasswordIsNull() {
        assertFalse(PasswordPolicy.isValid(null));
    }

    @Test
    void isValid_ShouldReturnTrue_WhenPasswordMatchesPolicy() {
        assertTrue(PasswordPolicy.isValid("Password1234"));
    }
}
