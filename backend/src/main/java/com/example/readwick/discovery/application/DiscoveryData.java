package com.example.readwick.discovery.application;

public record DiscoveryData(
        AuthorRecommendations byAuthor,
        CategoryRecommendations byCategory,
        SearchRecommendations bySearch) {
}
