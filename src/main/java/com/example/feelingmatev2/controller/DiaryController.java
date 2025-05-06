package com.example.feelingmatev2.controller;

import com.example.feelingmatev2.diary.DiaryService;
import com.example.feelingmatev2.diary.dto.DiaryRequest;
import com.example.feelingmatev2.diary.dto.DiaryResponse;
import com.example.feelingmatev2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryResponse> createDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody DiaryRequest diaryRequest
            ) {

        String loginId = userDetails.getUsername();
        DiaryResponse diaryResponse = diaryService.createDiary(loginId, diaryRequest);
        return ResponseEntity.ok(diaryResponse);
    }
}
