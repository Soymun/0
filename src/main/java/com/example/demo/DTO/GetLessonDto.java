package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GetLessonDto {

    private Long groupId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate day2;
}
