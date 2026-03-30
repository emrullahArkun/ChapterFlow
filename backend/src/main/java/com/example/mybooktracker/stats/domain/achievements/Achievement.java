package com.example.mybooktracker.stats.domain.achievements;

public record Achievement(
        AchievementType id,
        boolean unlocked,
        String unlockedDetail) {
}
