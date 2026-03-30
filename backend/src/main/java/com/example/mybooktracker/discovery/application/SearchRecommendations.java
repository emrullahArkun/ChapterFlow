package com.example.mybooktracker.discovery.application;

import com.example.mybooktracker.discovery.domain.DiscoveryBook;

import java.util.List;

public record SearchRecommendations(
        List<String> queries,
        List<DiscoveryBook> books) {

    public SearchRecommendations {
        queries = queries == null ? List.of() : List.copyOf(queries);
        books = books == null ? List.of() : List.copyOf(books);
    }
}
