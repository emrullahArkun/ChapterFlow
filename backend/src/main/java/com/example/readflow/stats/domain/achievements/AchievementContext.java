package com.example.readflow.stats.domain.achievements;

import com.example.readflow.sessions.domain.ReadingSession;

import java.time.ZoneId;
import java.util.List;

public record AchievementContext(
        long totalBooks,
        long completedBooks,
        long totalPages,
        long totalSessions,
        int maxDailyPages,
        int bestStreak,
        ZoneId zoneId,
        List<ReadingSession> sessions
) {}
