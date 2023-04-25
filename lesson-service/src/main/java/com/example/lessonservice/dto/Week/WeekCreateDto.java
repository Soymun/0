package com.example.lessonservice.dto.Week;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeekCreateDto {

    private LocalDate fromWeek;

    private LocalDate toWeek;
}
