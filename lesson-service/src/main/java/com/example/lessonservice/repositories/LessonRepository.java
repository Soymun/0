package com.example.lessonservice.repositories;


import com.example.lessonservice.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> getAllByDayBetween(LocalDate day, LocalDate day2);

    List<Lesson> getAllByNumber(Long number);

    Lesson getLessonById(Long id);
    
}
