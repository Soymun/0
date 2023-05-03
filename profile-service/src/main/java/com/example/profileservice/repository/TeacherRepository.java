package com.example.profileservice.repository;

import com.example.profileservice.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> getTeacherByName(String name);

    Teacher getTeachersByUniversityIdAndName(Long id, String name);

    List<Teacher> getTeachersByUniversityId(Long id);
}
