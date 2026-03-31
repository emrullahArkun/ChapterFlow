package com.example.mybooktracker.sessions.api;

import com.example.mybooktracker.sessions.api.dto.ReadingSessionDto;
import com.example.mybooktracker.sessions.domain.ReadingSession;
import org.springframework.stereotype.Component;

@Component
public class ReadingSessionMapper {

    public ReadingSessionDto toDto(ReadingSession session) {
        if (session == null) {
            return null;
        }

        return new ReadingSessionDto(
                session.getId(),
                session.getBook() != null ? session.getBook().getId() : null,
                session.getStartTime(),
                session.getEndTime(),
                session.getStatus(),
                session.getStartPage(),
                session.getEndPage(),
                session.getPagesRead(),
                session.getPausedMillis(),
                session.getPausedAt()
        );
    }
}
