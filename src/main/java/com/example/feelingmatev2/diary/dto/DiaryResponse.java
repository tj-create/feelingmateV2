package com.example.feelingmatev2.diary.dto;

import com.example.feelingmatev2.diary.Diary;
import com.example.feelingmatev2.emotion.EmotionType;

import java.time.LocalDateTime;

public record DiaryResponse(Long id,
                            String title,
                            String content,
                            EmotionType mainEmotion,
                            String subEmotion,
                            Integer emotionScore,
                            String selectedEmoji,
                            LocalDateTime createdAt) {

    public static DiaryResponse from(Diary diary) {
        return new DiaryResponse(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getMainEmotion(),
                diary.getSubEmotion(),
                diary.getEmotionScore(),
                diary.getSelectedEmoji(),
                diary.getCreateAt()
        );
    }
}
