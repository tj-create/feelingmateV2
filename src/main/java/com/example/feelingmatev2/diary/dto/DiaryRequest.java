package com.example.feelingmatev2.diary.dto;

import lombok.Getter;

public record DiaryRequest(String title, String content, boolean recommendOn) {
}
