package com.example.demo.DTO.Week;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeekDto {

    private Long id;

    private LocalDate fromWeek;

    private LocalDate toWeek;
}
