package com.example.readflow.sessions.api.dto;

import jakarta.validation.constraints.NotNull;

public record StartSessionRequest(
        @NotNull(message = "Book ID is required") Long bookId) {
}
