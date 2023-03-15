package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class LessonDto {

    private Long id;

    private String lesson;

    private LocalDateTime day;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private Long number;

    private String teacherName;

    private String classRoom;

    private String type;

    private String group;

    public LessonDto(Long id, String lesson, LocalDateTime day, LocalDateTime fromTime, LocalDateTime toTime, Long number, String teacherName, String classRoom, String type) {
        this.id = id;
        this.lesson = lesson;
        this.day = day;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.number = number;
        this.teacherName = teacherName;
        this.classRoom = classRoom;
        this.type = type;
    }

    public LessonDto(Long id, String lesson, LocalDateTime day, LocalDateTime fromTime, LocalDateTime toTime, Long number, String teacherName, String classRoom,String group, String type) {
        this.id = id;
        this.lesson = lesson;
        this.day = day;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.number = number;
        this.teacherName = teacherName;
        this.classRoom = classRoom;
        this.type = type;
        this.group = group;
    }
}
