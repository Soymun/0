package com.example.demo.Repositories;

import com.example.demo.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> getTeacherByTeacherName(String name);
}
