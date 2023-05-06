package com.example.courseservice.service;

import com.example.courseservice.dto.course.CourseCreateDto;
import com.example.courseservice.dto.course.CourseDto;
import com.example.courseservice.dto.course.CourseUpdateDto;

import java.util.List;

public interface CourseService {

    void saveCourse(CourseCreateDto courseCreateDto);

    CourseDto updateCourse(CourseUpdateDto courseUpdateDto);

    void deleteCourse(Long id);

    CourseDto getCourseById(Long id);

    CourseDto getCourseByNameAndNumberCourse(String name, Long numberCourse);

    List<CourseDto> getCourseByUniversityId(Long universityId);
}
