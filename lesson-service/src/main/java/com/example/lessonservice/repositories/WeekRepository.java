package com.example.lessonservice.repositories;

import com.example.lessonservice.entity.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekRepository extends JpaRepository<Week, Long> {

    Week findWeekById(Long id);

}

