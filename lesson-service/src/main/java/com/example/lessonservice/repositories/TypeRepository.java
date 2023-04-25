package com.example.lessonservice.repositories;

import com.example.lessonservice.entity.TypeOfLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<TypeOfLesson, Long> {

    Optional<TypeOfLesson> getTypeByType(String type);
}
