package com.example.readwick.stats.domain.achievements;

import com.example.readwick.sessions.domain.ReadingSession;

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
