package com.example.demo.DTO.Lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GetLessonDto {

    private Long groupId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate day2;
}
