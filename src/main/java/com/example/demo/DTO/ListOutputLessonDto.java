package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ListOutputLessonDto {

    private List<OutputLessonDto> outputLessonDtos;

    public ListOutputLessonDto() {
        this.outputLessonDtos = new ArrayList<>();
    }
}
