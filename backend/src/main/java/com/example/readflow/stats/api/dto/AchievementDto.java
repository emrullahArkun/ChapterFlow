package com.example.readflow.stats.api.dto;

import com.example.readflow.stats.domain.achievements.AchievementType;

public record AchievementDto(
        AchievementType id,
        boolean unlocked,
        String unlockedDetail) {
}
