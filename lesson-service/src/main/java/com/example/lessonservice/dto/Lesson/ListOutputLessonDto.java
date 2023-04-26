package com.example.lessonservice.dto.Lesson;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

@Data
@AllArgsConstructor
public class ListOutputLessonDto {

    private Map<OutputLessonDto, OutputLessonDto> outputLessonDtos;

    public ListOutputLessonDto() {
        this.outputLessonDtos = new TreeMap<>(Comparator.comparing(OutputLessonDto::getFromTime));
    }
}
