package com.example.readflow.sessions.api.dto;

import com.example.readflow.sessions.domain.SessionStatus;

import java.time.Instant;

public record ReadingSessionDto(
                Long id,
                Long bookId,
                Instant startTime,
                Instant endTime,
                SessionStatus status,
                Integer startPage,
                Integer endPage,
                Integer pagesRead,
                Long pausedMillis,
                Instant pausedAt) {
}
