package com.example.readflow.discovery.dto;

import jakarta.validation.constraints.NotBlank;

public record LogSearchRequest(@NotBlank String query) {}
