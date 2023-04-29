package com.example.lessonservice.dto.Week;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeekDto {

    private Long id;

    private Long numberWeek;

    private LocalDate fromWeek;

    private LocalDate toWeek;
}
