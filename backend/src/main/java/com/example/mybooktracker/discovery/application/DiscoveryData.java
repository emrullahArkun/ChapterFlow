package com.example.mybooktracker.discovery.application;

public record DiscoveryData(
        AuthorRecommendations byAuthor,
        CategoryRecommendations byCategory,
        SearchRecommendations bySearch) {
}
