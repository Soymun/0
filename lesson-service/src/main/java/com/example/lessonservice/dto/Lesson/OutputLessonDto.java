package com.example.lessonservice.dto.Lesson;

import com.example.lessonservice.dto.Course;
import com.example.lessonservice.dto.Teacher;
import com.example.lessonservice.entity.ClassRoom;
import com.example.lessonservice.entity.TypeOfLesson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OutputLessonDto {

    private Long id;

    private Course course;

    private LocalDate day;
    private LocalTime fromTime;

    private LocalTime toTime;

    private List<Long> number;

    private Teacher teacher;

    private ClassRoom classRoom;

    private TypeOfLesson type;

    public OutputLessonDto(LessonDto lessonDto, Course course, Teacher teacher){
        this.number = new ArrayList<>();
        this.id = lessonDto.getId();
        this.course = course;
        this.number.add(lessonDto.getNumber());
        this.fromTime = lessonDto.getFromTime();
        this.toTime = lessonDto.getToTime();
        this.teacher = teacher;
        this.classRoom = lessonDto.getClassRoom();
        this.type = lessonDto.getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutputLessonDto that = (OutputLessonDto) o;
        return Objects.equals(course, that.course) && Objects.equals(teacher, that.teacher) && Objects.equals(classRoom, that.classRoom) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, teacher, classRoom, type);
    }
}
