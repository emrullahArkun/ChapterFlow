package com.example.mybooktracker.stats.application;

import com.example.mybooktracker.stats.domain.achievements.*;
import com.example.mybooktracker.stats.domain.streak.StreakCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AchievementConfig {

    @Bean
    StreakCalculator streakCalculator() {
        return new StreakCalculator();
    }

    @Bean
    AchievementChecker bookwormChecker() {
        return new BookwormAchievementChecker();
    }

    @Bean
    AchievementChecker earlyBirdChecker() {
        return new EarlyBirdAchievementChecker();
    }

    @Bean
    AchievementChecker firstSessionChecker() {
        return new FirstSessionAchievementChecker();
    }

    @Bean
    AchievementChecker libraryBuilderChecker() {
        return new LibraryBuilderAchievementChecker();
    }

    @Bean
    AchievementChecker marathonChecker() {
        return new MarathonAchievementChecker();
    }

    @Bean
    AchievementChecker monthStreakChecker() {
        return new MonthStreakAchievementChecker();
    }

    @Bean
    AchievementChecker nightOwlChecker() {
        return new NightOwlAchievementChecker();
    }

    @Bean
    AchievementChecker pageTurnerChecker() {
        return new PageTurnerAchievementChecker();
    }

    @Bean
    AchievementChecker speedReaderChecker() {
        return new SpeedReaderAchievementChecker();
    }

    @Bean
    AchievementChecker weekStreakChecker() {
        return new WeekStreakAchievementChecker();
    }
}
