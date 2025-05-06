package com.example.feelingmatev2.diary;

import com.example.feelingmatev2.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
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
}
