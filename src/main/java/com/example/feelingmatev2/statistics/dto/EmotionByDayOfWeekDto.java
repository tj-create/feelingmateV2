package com.example.feelingmatev2.statistics.dto;

import com.example.feelingmatev2.emotion.EmotionType;

import java.time.DayOfWeek;

public record EmotionByDayOfWeekDto(DayOfWeek dayOfWeek, Double average, EmotionType emotion) {
}
