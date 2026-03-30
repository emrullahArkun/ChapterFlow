package com.example.mybooktracker.discovery.api.dto;

import java.util.List;

public record SearchResultDto(
        List<RecommendedBookDto> items,
        int totalItems) {
}
