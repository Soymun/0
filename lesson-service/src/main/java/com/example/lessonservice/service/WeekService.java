package com.example.lessonservice.service;



import com.example.lessonservice.dto.Week.WeekCreateDto;
import com.example.lessonservice.dto.Week.WeekDto;

import java.time.LocalDateTime;

public interface WeekService {

    WeekDto findWeekById(Long id);

    Long findWeekByDay(LocalDateTime day);

    WeekDto updateWeek(WeekDto weekDto);

    void deleteWeek(Long id);

    void saveWeek(WeekCreateDto weekCreateDto);
}
