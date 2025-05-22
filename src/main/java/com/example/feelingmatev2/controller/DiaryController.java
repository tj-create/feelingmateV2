package com.example.feelingmatev2.controller;

import com.example.feelingmatev2.diary.DiaryService;
import com.example.feelingmatev2.diary.dto.DiaryRequest;
import com.example.feelingmatev2.diary.dto.DiaryResponse;
import com.example.feelingmatev2.diary.emoji_dto.EmojiOptionResponse;
import com.example.feelingmatev2.diary.emoji_dto.EmojiSelectionRequest;
import com.example.feelingmatev2.emotion.EmotionService;
import com.example.feelingmatev2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;
    private final EmotionService emotionService;

    @PostMapping
    public ResponseEntity<DiaryResponse> createDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody DiaryRequest diaryRequest
    ) {

        String loginId = userDetails.getUsername();
        DiaryResponse diaryResponse = diaryService.createDiary(loginId, diaryRequest);
        return ResponseEntity.ok(diaryResponse);
    }

    @GetMapping("/my")
    public ResponseEntity<List<DiaryResponse>> getAllDiaries(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String loginId = userDetails.getUsername();
        List<DiaryResponse> diaryResponses = diaryService.selectDiaryAll(loginId);
        return ResponseEntity.ok(diaryResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryResponse> getDiaryById(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        String loginId = userDetails.getUsername();
        DiaryResponse diaryResponse = diaryService.selectDiaryById(loginId, id);
        return ResponseEntity.ok(diaryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiaryResponse> updateDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestBody DiaryRequest diaryRequest
    ) {
        String loginId = userDetails.getUsername();
        DiaryResponse diaryResponse = diaryService.updateDiary(loginId, id, diaryRequest);

        return ResponseEntity.ok(diaryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        String loginId = userDetails.getUsername();
        diaryService.deleteDiary(loginId, id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/emoji-option")
    public ResponseEntity<EmojiOptionResponse> getEmotionResult(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        String loginId = userDetails.getUsername();

        EmojiOptionResponse emotionResult = diaryService.getEmotionResult(loginId, id);

        return ResponseEntity.ok(emotionResult);
    }

    @PatchMapping("/{id}/emoji-option")
    public ResponseEntity<Void> selectEmoji(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestBody EmojiSelectionRequest request
    ) {

        String loginId = userDetails.getUsername();
        diaryService.selectEmoji(loginId, id, request.selectedEmoji());
        return ResponseEntity.ok().build();
    }
}
