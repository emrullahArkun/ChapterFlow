package com.example.readflow.auth.api.dto;

import com.example.readflow.auth.domain.User;

public record UserDto(
        Long id,
        String email,
        String role) {

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getRole().name());
    }
}
