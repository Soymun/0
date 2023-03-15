package com.example.demo.Repositories;

import com.example.demo.Entity.TypeOfLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<TypeOfLesson, Long> {

    Optional<TypeOfLesson> getTypeByType(String type);
}
