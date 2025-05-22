package com.example.feelingmatev2.diary;

import com.example.feelingmatev2.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findDiaryByUserAndId(User user, Long diaryId);
    List<Diary> findAllByUser(User user);

    @Query("SELECT AVG(d.emotionScore) FROM Diary d WHERE d.user = :user AND d.createAt BETWEEN :start AND :end")
    Double findAverageScoreThisMonth(@Param("user") User user,
                                     @Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);

    List<Diary> findByUserAndCreateAtBetweenOrderByCreateAtAsc(User user, LocalDateTime start, LocalDateTime end);


}
