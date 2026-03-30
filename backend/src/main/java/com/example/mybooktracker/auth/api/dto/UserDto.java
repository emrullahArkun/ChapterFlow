package com.example.mybooktracker.auth.api.dto;

import com.example.mybooktracker.auth.domain.User;

public record UserDto(
        Long id,
        String email,
        String role) {

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getRole().name());
    }
}
