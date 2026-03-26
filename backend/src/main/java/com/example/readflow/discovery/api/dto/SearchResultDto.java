package com.example.readflow.discovery.api.dto;

import java.util.List;

public record SearchResultDto(
        List<RecommendedBookDto> items,
        int totalItems) {
}
