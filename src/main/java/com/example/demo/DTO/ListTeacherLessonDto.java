package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ListTeacherLessonDto {

    private List<OutputTeacherLessonDto> teacherLessonDtos;

    public ListTeacherLessonDto() {
        this.teacherLessonDtos = new ArrayList<>();
    }
}
