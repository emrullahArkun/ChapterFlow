package com.example.readwick.discovery.api.dto;

import java.util.List;

public record SearchResultDto(
        List<RecommendedBookDto> items,
        int totalItems) {
}
