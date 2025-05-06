package com.example.feelingmatev2.diary;

import com.example.feelingmatev2.diary.dto.DiaryRequest;
import com.example.feelingmatev2.emotion.EmotionResult;
import com.example.feelingmatev2.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String content;
    private LocalDateTime createAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmotionType mainEmotion;
    @Column(columnDefinition = "TEXT")
    private String subEmotion;
    private Integer emotionScore;
    private boolean recommendOn;
    @Column(length = 10)
    private String selectedEmoji;  // 주 감정에 맞는 이모지 추천, 그 외의 이모지에서도 선택 가능 (전체 이모지 list 만들어야댐_

    public static Diary createDiary(User user, DiaryRequest diaryRequest) {
        Diary diary = new Diary();
        diary.user = user;
        diary.title = diaryRequest.title();
        diary.content = diaryRequest.content();
        diary.recommendOn = diaryRequest.recommendOn();

        return diary;
    }

    public void setEmotionResult(EmotionResult result) {
        this.mainEmotion = result.mainEmotion();
        this.subEmotion = result.subEmotion();
        this.emotionScore = result.emotionScore();
    }

    public void setEmoji(String selectEmoji) {
        this.selectedEmoji = selectEmoji;
    }
}
