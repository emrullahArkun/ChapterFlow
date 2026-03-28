package com.example.readwick.stats.application;

import com.example.readwick.auth.domain.User;
import com.example.readwick.sessions.domain.SessionStatus;
import com.example.readwick.sessions.infra.persistence.ReadingSessionRepository;
import com.example.readwick.shared.time.ZoneIdResolver;
import com.example.readwick.stats.domain.streak.StreakCalculator;
import com.example.readwick.stats.domain.streak.StreakInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StreakService {

    private final ReadingSessionRepository sessionRepository;
    private final StreakCalculator streakCalculator;
    private final Clock clock;

    public StreakInfo calculateStreaks(User user) {
        return calculateStreaks(user, ZoneIdResolver.resolveOrUtc(null));
    }

    public StreakInfo calculateStreaks(User user, String timezone) {
        return calculateStreaks(user, ZoneIdResolver.resolveOrUtc(timezone));
    }

    public StreakInfo calculateStreaks(User user, ZoneId zoneId) {
        LocalDate today = LocalDate.now(clock.withZone(zoneId));

        List<LocalDate> readingDays = sessionRepository
                .findAllCompletedEndTimes(user, SessionStatus.COMPLETED)
                .stream()
                .map(instant -> instant.atZone(zoneId).toLocalDate())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();

        return streakCalculator.calculate(readingDays, today);
    }
}
