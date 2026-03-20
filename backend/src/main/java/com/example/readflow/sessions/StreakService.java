package com.example.readflow.sessions;

import com.example.readflow.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StreakService {

    private final ReadingSessionRepository sessionRepository;

    public int calculateCurrentStreak(User user) {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        List<LocalDate> readingDays = sessionRepository.findDistinctReadingDays(
                user, today.minusYears(1));

        if (readingDays.isEmpty()) return 0;

        int streak = 0;
        LocalDate expected = today;

        if (!readingDays.contains(expected)) {
            expected = expected.minusDays(1);
        }

        for (LocalDate day : readingDays) {
            if (day.equals(expected)) {
                streak++;
                expected = expected.minusDays(1);
            } else if (day.isBefore(expected)) {
                break;
            }
        }

        return streak;
    }

    public int calculateLongestStreak(User user) {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        List<LocalDate> readingDays = sessionRepository.findDistinctReadingDays(
                user, today.minusYears(1));

        if (readingDays.isEmpty()) return 0;

        int longest = 1;
        int current = 1;
        for (int i = readingDays.size() - 2; i >= 0; i--) {
            if (readingDays.get(i).minusDays(1).equals(readingDays.get(i + 1))) {
                current++;
                longest = Math.max(longest, current);
            } else {
                current = 1;
            }
        }

        return longest;
    }
}
