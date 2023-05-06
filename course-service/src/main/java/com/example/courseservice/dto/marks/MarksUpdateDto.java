package com.example.courseservice.dto.marks;

import com.example.courseservice.entity.Mark;
import lombok.Data;

@Data
public class MarksUpdateDto {

    private Long id;

    private Mark mark;
}
