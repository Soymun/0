package com.example.demo.Repositories;

import com.example.demo.Entity.LessonName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonNameRepository extends JpaRepository<LessonName, Long> {

    Optional<LessonName> getLessonNameByName(String name);
}
