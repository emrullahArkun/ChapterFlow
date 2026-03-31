package com.example.mybooktracker.stats.domain.achievements;

public class FirstSessionAchievementChecker implements AchievementChecker {

    @Override
    public AchievementType type() {
        return AchievementType.FIRST_SESSION;
    }

    @Override
    public Achievement check(AchievementContext context) {
        return new Achievement(
                type(),
                context.totalSessions() >= 1,
                context.totalSessions() >= 1 ? context.totalSessions() + " sessions" : null);
    }
}
