package com.example.courseservice.repositories;

import com.example.courseservice.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository extends JpaRepository<Courses, Long> {

    Optional<Courses> getCoursesByNumberOfCourseAndName(Long numberOfCourse, String name);

    List<Courses> getCoursesByUniversityId(Long universityId);
}
