package com.example.feelingmatev2.statistics.dto;

import java.util.List;

public record StatisticsTotalResponse(
        Double averageScore,
        List<EmotionTrendDto> emotionTrendThisMonth,
        List<EmotionCountDto> emotionCountTop3ThisMonth, List<EmotionByDayOfWeekDto> emotionByDayOfWeek,
        List<EmotionCalender> emotionCalender) {
}
