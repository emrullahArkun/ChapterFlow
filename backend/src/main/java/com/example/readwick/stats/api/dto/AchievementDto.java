package com.example.readwick.stats.api.dto;

import com.example.readwick.stats.domain.achievements.AchievementType;

public record AchievementDto(
        AchievementType id,
        boolean unlocked,
        String unlockedDetail) {
}
