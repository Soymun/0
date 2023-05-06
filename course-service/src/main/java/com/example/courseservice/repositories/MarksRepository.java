package com.example.courseservice.repositories;

import com.example.courseservice.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {

    Marks getMarksById(Long id);

    List<Marks> getMarksByStudentId(Long id);
}
