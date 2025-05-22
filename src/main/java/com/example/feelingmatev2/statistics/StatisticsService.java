package com.example.feelingmatev2.statistics;

import com.example.feelingmatev2.diary.Diary;
import com.example.feelingmatev2.diary.DiaryRepository;
import com.example.feelingmatev2.emotion.EmotionType;
import com.example.feelingmatev2.statistics.dto.*;
import com.example.feelingmatev2.user.User;
import com.example.feelingmatev2.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {


    //5	감정 캘린더	/api/statistics/calendar

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    public double getAverageScore(String loginId) {
        User user = getUser(loginId);

        LocalDate now = LocalDate.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);

        return diaryRepository.findAverageScoreThisMonth(user, startOfMonth, endOfMonth);
    }

    public List<EmotionTrendDto> getEmotionTrendThisMonth(String loginId) {
        return extractedDiariesThisMonth(loginId).stream()
                .map(d -> new EmotionTrendDto(d.getCreateAt().toLocalDate(), d.getEmotionScore())).collect(Collectors.toList());
    }

    public List<EmotionCountDto> getEmotionCountTop3ThisMonth(String loginId) {

        return extractedDiariesThisMonth(loginId).stream()
                .collect(Collectors.groupingBy(Diary::getMainEmotion, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<EmotionType, Long>comparingByValue().reversed())
                .limit(3)
                .map(entry -> new EmotionCountDto(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<EmotionByDayOfWeekDto> getEmotionByDayOfWeek(String loginId) {
        List<Diary> diaries = extractedDiariesThisMonth(loginId);

        Map<DayOfWeek, List<Diary>> dayOfWeekListMap = diaries.stream()
                .collect(Collectors.groupingBy(d -> d.getCreateAt().getDayOfWeek()));

        return getDayOfWeekDtos(dayOfWeekListMap);
    }

    public List<EmotionCalender> getEmotionCalender(String loginId) {
        List<Diary> diaries = extractedDiariesThisMonth(loginId);

        Map<LocalDate, Diary> latestDiaryByDate = diaries.stream()
                .collect(Collectors.toMap(
                        d -> d.getCreateAt().toLocalDate(),
                        d -> d,
                        (existing, replacement) -> replacement // 중복 시 마지막 일기로 대체
                ));


        return latestDiaryByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // LocalDate 오름차순 정렬
                .map(entry -> new EmotionCalender(
                        entry.getKey(),
                        entry.getValue().getSelectedEmoji()
                ))
                .toList();
    }

    private List<Diary> extractedDiariesThisMonth(String loginId) {
        User user = getUser(loginId);

        LocalDate now = LocalDate.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);

        return diaryRepository.findByUserAndCreateAtBetweenOrderByCreateAtAsc(user, startOfMonth, endOfMonth);
    }

    public StatisticsTotalResponse getTotalStatistics(String loginId) {

        double averageScore = getAverageScore(loginId);
        System.out.println("1");
        List<EmotionTrendDto> emotionTrendThisMonth = getEmotionTrendThisMonth(loginId);
        System.out.println("2");
        List<EmotionCountDto> emotionCountTop3ThisMonth = getEmotionCountTop3ThisMonth(loginId);
        System.out.println("3");
        List<EmotionByDayOfWeekDto> emotionByDayOfWeek = getEmotionByDayOfWeek(loginId);
        System.out.println("4");
        List<EmotionCalender> emotionCalender = getEmotionCalender(loginId);


        return new StatisticsTotalResponse(averageScore, emotionTrendThisMonth,
                emotionCountTop3ThisMonth, emotionByDayOfWeek, emotionCalender);

    }

    private static List<EmotionByDayOfWeekDto> getDayOfWeekDtos(Map<DayOfWeek, List<Diary>> dayOfWeekListMap) {
        return dayOfWeekListMap.entrySet().stream()
                .map(entry ->
                {
                    DayOfWeek key = entry.getKey();
                    List<Diary> dayDiaries = entry.getValue();

                    double avg = dayDiaries.stream()
                            .mapToInt(Diary::getEmotionScore)
                            .average()
                            .orElse(0);

                    EmotionType emotionType = dayDiaries.stream()
                            .collect(Collectors.groupingBy(Diary::getMainEmotion, Collectors.counting()))
                            .entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .orElse(EmotionType.NEUTRAL);

                    return new EmotionByDayOfWeekDto(key, avg, emotionType);
                })
                .sorted(Comparator.comparingInt(d -> d.dayOfWeek().getValue()))
                .toList();
    }

    private User getUser(String loginId) {
        return userRepository.findUserByLoginId(loginId)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 회원")
                );
    }
}
