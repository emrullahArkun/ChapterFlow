package com.example.readflow.discovery.domain;

import java.util.List;
import java.util.Set;

public record DiscoverySnapshot(
        Set<String> ownedIsbns,
        List<String> topAuthors,
        List<String> topCategories,
        List<String> recentSearches
) {}
