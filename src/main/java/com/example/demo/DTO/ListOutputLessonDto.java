package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class ListOutputLessonDto {

    private Map<OutputLessonDto, OutputLessonDto> outputLessonDtos;

    public ListOutputLessonDto() {
        this.outputLessonDtos = new LinkedHashMap<>();
    }
}
