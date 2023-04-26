package com.example.lessonservice.dto.Lesson;

import com.example.lessonservice.dto.Course;
import com.example.lessonservice.dto.Teacher;
import com.example.lessonservice.entity.ClassRoom;
import com.example.lessonservice.entity.TypeOfLesson;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GetUpdateLessonDto {

    private List<Long> ids;

    private Course course;

    private LocalDate day;

    private LocalTime fromTime;

    private LocalTime toTime;

    private Long number;

    private Teacher teacher;

    private ClassRoom classRoom;

    private TypeOfLesson type;

    public GetUpdateLessonDto() {
        this.ids = new ArrayList<>();
    }
}
