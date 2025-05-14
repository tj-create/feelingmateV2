package com.example.feelingmatev2.emotion;

import lombok.Getter;

import java.util.List;

@Getter
public enum EmotionType {
    JOY(List.of("😊", "😁", "🥰")),
    SADNESS(List.of("😢", "😭", "😞")),
    ANGER(List.of("😠", "😡", "😤")),
    FEAR(List.of("😨", "😰", "😱")),
    NEUTRAL(List.of("😐", "😶", "🤔"));

    private final List<String> emojis;

    EmotionType(List<String> emojis) {
        this.emojis = emojis;
    }
}
