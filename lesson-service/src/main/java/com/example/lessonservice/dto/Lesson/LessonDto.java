package com.example.lessonservice.dto.Lesson;

import com.example.lessonservice.entity.ClassRoom;
import com.example.lessonservice.entity.TypeOfLesson;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class LessonDto {

    private Long id;

    private Long courseId;

    private LocalDate day;

    private LocalTime fromTime;

    private LocalTime toTime;

    private Long number;

    private Long teacherId;

    private ClassRoom classRoom;

    private TypeOfLesson type;

    private Long groupId;
}
