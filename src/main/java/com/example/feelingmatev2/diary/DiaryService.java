package com.example.feelingmatev2.diary;

import com.example.feelingmatev2.diary.dto.DiaryRequest;
import com.example.feelingmatev2.diary.dto.DiaryResponse;
import com.example.feelingmatev2.emotion.EmotionResult;
import com.example.feelingmatev2.emotion.EmotionService;
import com.example.feelingmatev2.user.User;
import com.example.feelingmatev2.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final EmotionService emotionService;

    // CRUD 기능
    @Transactional
    public DiaryResponse createDiary(String loginId, DiaryRequest diaryRequest) {
        User user = userRepository.findUserByLoginId(loginId).orElseThrow(
                () -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        Diary diary = Diary.createDiary(user, diaryRequest);

        if (diaryRequest.recommendOn()) {
            // chat gpt api 통신 연결 기능
            EmotionResult result = emotionService.analyze(diaryRequest.content());
            diary.setEmotionResult(result);
        }

        return DiaryResponse.from(diaryRepository.save(diary));
    }

    @Transactional
    public void selectEmoji(Long diaryId, String selectEmoji) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new IllegalStateException("해당 일기를 찾을 수 없습니다.")
        );

        diary.setEmoji(selectEmoji);
    }

    // 조회 (하나 조회, 전체 조회)
    // 수정
    // 삭제


}
