package com.example.readwick.sessions.api.dto;

import com.example.readwick.sessions.domain.SessionStatus;

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
