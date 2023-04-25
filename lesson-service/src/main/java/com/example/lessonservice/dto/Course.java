package com.example.lessonservice.dto;

import lombok.Data;

@Data
public class Course {

    private Long id;
    private String name;

    private Long numberOfCourse;
    private Long teacherId;
}
