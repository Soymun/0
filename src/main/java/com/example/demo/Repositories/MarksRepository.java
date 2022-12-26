package com.example.demo.Repositories;

import com.example.demo.Entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {

    Marks getMarksById(Long id);

    List<Marks> getMarksByUserId(Long id);
}
