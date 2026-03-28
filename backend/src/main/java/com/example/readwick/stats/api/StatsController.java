package com.example.readwick.stats.api;

import com.example.readwick.auth.domain.User;
import com.example.readwick.stats.application.AchievementService;
import com.example.readwick.stats.application.StatsService;
import com.example.readwick.stats.application.StreakService;
import com.example.readwick.shared.security.CurrentUser;
import com.example.readwick.stats.api.dto.AchievementDto;
import com.example.readwick.stats.api.dto.StatsOverviewDto;
import com.example.readwick.stats.domain.streak.StreakInfo;
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
