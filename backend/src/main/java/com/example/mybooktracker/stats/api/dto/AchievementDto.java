package com.example.mybooktracker.stats.api.dto;

import com.example.mybooktracker.stats.domain.achievements.AchievementType;

public record AchievementDto(
        AchievementType id,
        boolean unlocked,
        String unlockedDetail) {
}
