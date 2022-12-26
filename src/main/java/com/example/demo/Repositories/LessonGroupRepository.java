package com.example.demo.Repositories;

import com.example.demo.Entity.LessonGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonGroupRepository extends JpaRepository<LessonGroup, Long> {


    void deleteByLessonId(Long id);
}
