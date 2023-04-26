package com.example.lessonservice.dto.Lesson;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class LessonToUpdateDto {

    private List<Long> ids;

    private Long courseId;

    private Long classRoomId;

    private Long teacherId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOfWeek;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime fromTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime toTime;

    private Long typeId;

    private Long groupId;
}
