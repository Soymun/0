package com.example.demo.DTO;

import com.example.demo.Entity.ClassRoom;
import com.example.demo.Entity.TypeOfLesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLesson {

    private Long id;

    private Long courseId;

    private LocalDate day;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private Long number;

    private Long teacherId;

    private ClassRoom classRoom;

    private TypeOfLesson type;

    private Long groupId;
}
