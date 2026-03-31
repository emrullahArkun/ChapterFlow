package com.example.mybooktracker.auth.api.dto;

import com.example.mybooktracker.auth.application.PasswordPolicy;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank
        @Pattern(regexp = PasswordPolicy.REGEX, message = PasswordPolicy.VALIDATION_MESSAGE)
        String password) {
}
