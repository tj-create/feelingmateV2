package com.example.feelingmatev2.diary;

import com.example.feelingmatev2.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findDiaryByUserAndId(User user, Long diaryId);

    List<Diary> findAllByUser(User user);
}
