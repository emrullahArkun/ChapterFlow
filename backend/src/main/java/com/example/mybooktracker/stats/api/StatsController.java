package com.example.mybooktracker.stats.api;

import com.example.mybooktracker.auth.domain.User;
import com.example.mybooktracker.stats.application.AchievementService;
import com.example.mybooktracker.stats.application.StatsService;
import com.example.mybooktracker.stats.application.StreakService;
import com.example.mybooktracker.shared.security.CurrentUser;
import com.example.mybooktracker.stats.api.dto.AchievementDto;
import com.example.mybooktracker.stats.api.dto.StatsOverviewDto;
import com.example.mybooktracker.stats.domain.streak.StreakInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;
    private final AchievementService achievementService;
    private final StreakService streakService;

    @GetMapping("/overview")
    public ResponseEntity<StatsOverviewDto> getOverview(
            @CurrentUser User user,
            @RequestHeader(value = "X-Timezone", required = false) String timezone) {
        return ResponseEntity.ok(StatsApiMapper.toDto(statsService.getOverview(user, timezone)));
    }

    @GetMapping("/achievements")
    public ResponseEntity<List<AchievementDto>> getAchievements(
            @CurrentUser User user,
            @RequestHeader(value = "X-Timezone", required = false) String timezone) {
        return ResponseEntity.ok(StatsApiMapper.toAchievementDtos(achievementService.getAchievements(user, timezone)));
    }

    @GetMapping("/streak")
    public ResponseEntity<Map<String, Integer>> getStreak(
            @CurrentUser User user,
            @RequestHeader(value = "X-Timezone", required = false) String timezone) {
        StreakInfo streakInfo = streakService.calculateStreaks(user, timezone);
        return ResponseEntity.ok(Map.of(
                "currentStreak", streakInfo.current(),
                "longestStreak", streakInfo.longest()
        ));
    }
}
