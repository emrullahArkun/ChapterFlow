package com.example.readflow.stats.api.dto;

import java.time.LocalDate;

public record DailyActivityDto(LocalDate date, int pagesRead) {
}
