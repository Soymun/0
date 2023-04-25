package com.example.lessonservice.repositories;


import com.example.lessonservice.entity.LessonGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonGroupRepository extends JpaRepository<LessonGroup, Long> {


    void deleteByLessonId(Long id);
}
