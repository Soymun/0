package com.example.demo.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class OutputTeacherLessonDto {

    private Long id;

    private String lesson;

    private LocalDateTime day;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private List<Long> number;

    private String teacherName;

    private String classRoom;

    private Set<String> group;

    public OutputTeacherLessonDto(TeacherLessonDto teacherLessonDto) {
        this.number = new ArrayList<>();
        this.group = new HashSet<>();
        this.id = teacherLessonDto.getId();
        this.lesson = teacherLessonDto.getLesson();
        this.day = teacherLessonDto.getDay();
        this.fromTime = teacherLessonDto.getFromTime();
        this.toTime = teacherLessonDto.getToTime();
        number.add(teacherLessonDto.getNumber());
        this.teacherName = teacherLessonDto.getTeacherName();
        this.classRoom = teacherLessonDto.getClassRoom();
        group.add(teacherLessonDto.getGroup());
    }
}
