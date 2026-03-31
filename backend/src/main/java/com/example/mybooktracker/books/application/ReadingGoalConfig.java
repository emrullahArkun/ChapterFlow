package com.example.mybooktracker.books.application;

import com.example.mybooktracker.books.domain.ReadingGoalPeriodCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReadingGoalConfig {

    @Bean
    ReadingGoalPeriodCalculator readingGoalPeriodCalculator() {
        return new ReadingGoalPeriodCalculator();
    }
}
