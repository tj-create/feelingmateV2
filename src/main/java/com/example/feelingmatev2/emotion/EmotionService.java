package com.example.feelingmatev2.emotion;

import com.example.feelingmatev2.diary.EmotionType;
import org.springframework.stereotype.Service;

@Service
public class EmotionService {
    public EmotionResult analyze(String content) {
        return new EmotionResult(EmotionType.JOY, "좋은 하루", 96);
    }
}
