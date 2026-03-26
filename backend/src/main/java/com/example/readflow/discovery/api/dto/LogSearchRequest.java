package com.example.readflow.discovery.api.dto;

import jakarta.validation.constraints.NotBlank;

public record LogSearchRequest(@NotBlank String query) {}
