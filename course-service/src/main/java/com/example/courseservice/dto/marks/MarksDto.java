package com.example.courseservice.dto.marks;

import com.example.courseservice.entity.Courses;
import com.example.courseservice.entity.Mark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksDto {

    private Long id;

    private Long studentId;

    private Courses course;

    private Mark mark;
}
