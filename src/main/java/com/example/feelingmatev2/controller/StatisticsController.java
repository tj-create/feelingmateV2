package com.example.feelingmatev2.controller;

import com.example.feelingmatev2.security.UserDetailsImpl;
import com.example.feelingmatev2.statistics.StatisticsService;
import com.example.feelingmatev2.statistics.dto.StatisticsTotalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping()
    public ResponseEntity<StatisticsTotalResponse> getTotalStatistics(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        String loginId = userDetails.getUsername();

        return ResponseEntity.ok(statisticsService.getTotalStatistics(loginId));
    }
}
