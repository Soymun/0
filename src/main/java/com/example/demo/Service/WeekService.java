package com.example.demo.Service;

import com.example.demo.Entity.Week;

import java.time.LocalDateTime;

public interface WeekService {

    Week findWeekById(Long id);

    Long findWeekId(LocalDateTime dateTime);

}
