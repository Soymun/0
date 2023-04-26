package com.example.lessonservice.dto.Lesson;

import lombok.Data;

import java.util.List;

@Data
public class LessonCreateDto {

    private List<Long> weeks;

    private String day;

    private Long groupId;

    private Long courseId;

    private Long classRoomId;

    private Long teacherId;

    private Long number;

    private Long typeId;
}
