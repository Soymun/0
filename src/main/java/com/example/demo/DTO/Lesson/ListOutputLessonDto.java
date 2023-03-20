package com.example.demo.DTO.Lesson;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ListOutputLessonDto {

    private Map<OutputLessonDto, OutputLessonDto> outputLessonDtos;

    public ListOutputLessonDto() {
        this.outputLessonDtos = new LinkedHashMap<>();
    }
}
