package com.example.feelingmatev2;

import com.example.feelingmatev2.diary.DiaryService;
import com.example.feelingmatev2.diary.dto.DiaryRequest;
import com.example.feelingmatev2.security.AuthService;
import com.example.feelingmatev2.user.dto.SignupRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Init {

    private final AuthService authService;
    private final DiaryService diaryService;


    @PostConstruct
    public void init() {
        authService.signup(new SignupRequest("test", "1234", "sa"));
        diaryService.createDiary("test", new DiaryRequest("test title", "test content", true));
    }
}
