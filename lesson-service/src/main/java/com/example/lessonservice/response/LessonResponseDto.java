package com.example.lessonservice.response;

import com.example.lessonservice.dto.Course;
import com.example.lessonservice.dto.Group;
import com.example.lessonservice.dto.Teacher;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class LessonResponseDto {

    private Long id;

    private Course course;

    private LocalDate day;

    private LocalTime fromTime;

    private LocalTime toTime;

    private Long number;

    private Teacher teacher;

    private String classRoom;

    private String type;

    private Group group;
}
