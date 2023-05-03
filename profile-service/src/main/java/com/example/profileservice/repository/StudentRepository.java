package com.example.profileservice.repository;

import com.example.profileservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> getStudentsByGroupId(Long id);
}
