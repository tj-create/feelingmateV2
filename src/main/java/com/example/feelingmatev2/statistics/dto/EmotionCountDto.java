package com.example.feelingmatev2.statistics.dto;

import com.example.feelingmatev2.emotion.EmotionType;

public record EmotionCountDto(EmotionType emotion, Long count) {
}
