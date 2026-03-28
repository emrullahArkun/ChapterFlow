package com.example.readwick.stats.domain.achievements;

public record Achievement(
        AchievementType id,
        boolean unlocked,
        String unlockedDetail) {
}
