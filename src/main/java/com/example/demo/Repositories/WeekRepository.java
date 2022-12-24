package com.example.demo.Repositories;

import com.example.demo.Entity.Week;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface WeekRepository extends JpaRepository<Week, Long> {

    Week findWeekById(Long id);

}

