package com.example.mybooktracker.stats.api.dto;

import java.time.LocalDate;

public record DailyActivityDto(LocalDate date, int pagesRead) {
}
