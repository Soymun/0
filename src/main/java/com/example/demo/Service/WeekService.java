package com.example.demo.Service;

import com.example.demo.DTO.Week.WeekCreateDto;
import com.example.demo.DTO.Week.WeekDto;
import com.example.demo.Entity.Week;

import java.time.LocalDateTime;

public interface WeekService {

    WeekDto findWeekById(Long id);

    Long findWeekByDay(LocalDateTime day);

    WeekDto updateWeek(WeekDto weekDto);

    void deleteWeek(Long id);

    void saveWeek(WeekCreateDto weekCreateDto);
}
