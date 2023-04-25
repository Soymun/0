package com.example.lessonservice.dto.Lesson;

import lombok.Data;

import java.util.List;

@Data
public class DeleteLessons {

    private List<Long> lessonIds;
}
