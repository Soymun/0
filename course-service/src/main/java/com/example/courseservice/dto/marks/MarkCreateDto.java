package com.example.courseservice.dto.marks;

import com.example.courseservice.entity.Mark;
import lombok.Data;

@Data
public class MarkCreateDto {

    private Long studentId;

    private Long coursesId;

    private Mark mark;
}
