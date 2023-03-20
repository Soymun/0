package com.example.demo.DTO.Lesson;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutputLessonDto that = (OutputLessonDto) o;
        return Objects.equals(lesson, that.lesson) && Objects.equals(teacherName, that.teacherName) && Objects.equals(classRoom, that.classRoom) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lesson, teacherName, classRoom, type);
    }

    @Override
    public String toString() {
        return "outputLessonDto";
    }
}
