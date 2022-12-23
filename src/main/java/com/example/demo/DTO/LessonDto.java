package com.example.demo.DTO;

import com.example.demo.Entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
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

    private List<Long> group;

    public LessonDto(Long id, String lesson, LocalDateTime day, LocalDateTime fromTime, LocalDateTime toTime, Long number, String teacherName, String classRoom) {
        this.id = id;
        this.lesson = lesson;
        this.day = day;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.number = number;
        this.teacherName = teacherName;
        this.classRoom = classRoom;
    }
}
