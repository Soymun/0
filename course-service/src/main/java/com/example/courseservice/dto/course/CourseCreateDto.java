package com.example.courseservice.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateDto {

    private String name;

    private Long numberOfCourse;

    private Long teacherId;

    private Long universityId;
}
