package com.example.courseservice.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateDto {

    private Long id;

    private String name;

    private Long numberOfCourse;

    private Long teacherId;
}
