package com.example.readwick.discovery.api.dto;

import jakarta.validation.constraints.NotBlank;

public record LogSearchRequest(@NotBlank String query) {}
