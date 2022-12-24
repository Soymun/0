package com.example.demo.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OutputLessonDto {

    private Long id;

    private String lesson;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private List<Long> number;

    private String teacherName;

    private String classRoom;

    private String type;

    public OutputLessonDto(LessonDto lessonDto){
        this.number = new ArrayList<>();
        this.id = lessonDto.getId();
        this.lesson = lessonDto.getLesson();
        this.number.add(lessonDto.getNumber());
        this.fromTime = lessonDto.getFromTime();
        this.toTime = lessonDto.getToTime();
        this.teacherName = lessonDto.getTeacherName();
        this.classRoom = lessonDto.getClassRoom();
        this.type = lessonDto.getType();
    }
}
