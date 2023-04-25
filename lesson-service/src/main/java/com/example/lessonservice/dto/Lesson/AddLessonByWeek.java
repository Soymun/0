package com.example.lessonservice.dto.Lesson;


import lombok.Data;

import java.util.List;

@Data
public class AddLessonByWeek {

    private List<Long> weeks;

    private String day;

    private Long groupId;

    private String lesson;

    private String classRoom;

    private String teacher;

    private Long number;

    private String type;
}
