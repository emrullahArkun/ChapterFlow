package com.example.mybooktracker.stats.application;

import java.time.LocalDate;

public record DailyActivity(LocalDate date, int pagesRead) {
}
