package com.example.readflow.sessions;

import com.example.readflow.auth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StreakServiceTest {

    @Mock
    private ReadingSessionRepository sessionRepository;

    @InjectMocks
    private StreakService streakService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
    }

    // --- calculateCurrentStreak ---

    @Test
    void calculateCurrentStreak_ShouldReturnZero_WhenNoReadingDays() {
        when(sessionRepository.findDistinctReadingDays(eq(user), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        assertEquals(0, streakService.calculateCurrentStreak(user));
    }

    @Test
    void calculateCurrentStreak_ShouldCountConsecutiveDays_IncludingToday() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        when(sessionRepository.findDistinctReadingDays(eq(user), any(LocalDate.class)))
                .thenReturn(List.of(today, today.minusDays(1), today.minusDays(2)));

        assertEquals(3, streakService.calculateCurrentStreak(user));
    }

    @Test
    void calculateCurrentStreak_ShouldCountFromYesterday_WhenNoSessionToday() {
        LocalDate yesterday = LocalDate.now(ZoneOffset.UTC).minusDays(1);
        when(sessionRepository.findDistinctReadingDays(eq(user), any(LocalDate.class)))
                .thenReturn(List.of(yesterday, yesterday.minusDays(1)));

        assertEquals(2, streakService.calculateCurrentStreak(user));
    }

    @Test
    void calculateCurrentStreak_ShouldBreakOnGap() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        when(sessionRepository.findDistinctReadingDays(eq(user), any(LocalDate.class)))
                .thenReturn(List.of(today, today.minusDays(1), today.minusDays(3)));

        assertEquals(2, streakService.calculateCurrentStreak(user));
    }

    @Test
    void calculateCurrentStreak_ShouldReturnZero_WhenLastReadingWasTwoDaysAgo() {
        LocalDate twoDaysAgo = LocalDate.now(ZoneOffset.UTC).minusDays(2);
        when(sessionRepository.findDistinctReadingDays(eq(user), any(LocalDate.class)))
                .thenReturn(List.of(twoDaysAgo));

        assertEquals(0, streakService.calculateCurrentStreak(user));
    }

    // --- calculateLongestStreak ---

    @Test
    void calculateLongestStreak_ShouldReturnZero_WhenNoReadingDays() {
        when(sessionRepository.findDistinctReadingDays(eq(user), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        assertEquals(0, streakService.calculateLongestStreak(user));
    }

    @Test
    void calculateLongestStreak_ShouldFindLongestConsecutiveRun() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        when(sessionRepository.findDistinctReadingDays(eq(user), any(LocalDate.class)))
                .thenReturn(List.of(
                        today, today.minusDays(1),
                        today.minusDays(5), today.minusDays(6), today.minusDays(7)
                ));

        assertEquals(3, streakService.calculateLongestStreak(user));
    }

    @Test
    void calculateLongestStreak_ShouldReturnOne_WhenSingleDay() {
        when(sessionRepository.findDistinctReadingDays(eq(user), any(LocalDate.class)))
                .thenReturn(List.of(LocalDate.now(ZoneOffset.UTC)));

        assertEquals(1, streakService.calculateLongestStreak(user));
    }
}
