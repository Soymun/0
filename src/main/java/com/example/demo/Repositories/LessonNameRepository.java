package com.example.demo.Repositories;

import com.example.demo.Entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonNameRepository extends JpaRepository<Courses, Long> {

    Optional<Courses> getLessonNameByName(String name);
}
