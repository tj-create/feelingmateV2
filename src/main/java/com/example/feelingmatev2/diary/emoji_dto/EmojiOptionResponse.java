package com.example.feelingmatev2.diary.emoji_dto;

import com.example.feelingmatev2.emotion.EmotionResult;

import java.util.List;

public record EmojiOptionResponse(EmotionResult result, List<String> emojis) {
}
