package com.example.feelingmatev2.emotion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmotionService {

    public EmotionResult analyze(String content) {
        return new EmotionResult(EmotionType.JOY, "좋은 하루", 96);
    }

}
