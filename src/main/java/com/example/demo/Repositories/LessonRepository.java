package com.example.demo.Repositories;

import com.example.demo.Entity.Group;
import com.example.demo.Entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> getAllByDayBetween(LocalDateTime day, LocalDateTime day2);

    List<Lesson> getAllByNumber(Long number);

    Lesson getLessonById(Long id);

    List<Lesson> getAllByGroupInAndDayBetween(List<Group> group, LocalDateTime day, LocalDateTime day2);
}
