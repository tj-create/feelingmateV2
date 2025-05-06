package com.example.feelingmatev2.emotion;

import com.example.feelingmatev2.diary.EmotionType;

public record EmotionResult(EmotionType mainEmotion, String subEmotion, Integer emotionScore) {
}
