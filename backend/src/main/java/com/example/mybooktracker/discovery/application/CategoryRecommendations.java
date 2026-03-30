package com.example.mybooktracker.discovery.application;

import com.example.mybooktracker.discovery.domain.DiscoveryBook;

import java.util.List;

public record CategoryRecommendations(
        List<String> categories,
        List<DiscoveryBook> books) {

    public CategoryRecommendations {
        categories = categories == null ? List.of() : List.copyOf(categories);
        books = books == null ? List.of() : List.copyOf(books);
    }
}
