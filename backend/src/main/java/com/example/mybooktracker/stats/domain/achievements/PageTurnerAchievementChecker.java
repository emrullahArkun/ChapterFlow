package com.example.mybooktracker.stats.domain.achievements;

public class PageTurnerAchievementChecker extends ThresholdAchievementChecker {

    @Override
    public AchievementType type() {
        return AchievementType.PAGE_TURNER;
    }

    @Override
    protected long actual(AchievementContext context) {
        return context.totalPages();
    }

    @Override
    protected long threshold() {
        return 1000;
    }

    @Override
    protected String unit() {
        return "pages";
    }
}
