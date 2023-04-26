package com.example.lessonservice.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class Course {

    private Long id;
    private String name;
    private Long numberOfCourse;
    private Long teacherId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(numberOfCourse, course.numberOfCourse) && Objects.equals(teacherId, course.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfCourse, teacherId);
    }
}
