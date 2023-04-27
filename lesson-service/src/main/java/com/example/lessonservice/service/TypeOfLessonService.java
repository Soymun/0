package com.example.lessonservice.service;

import com.example.lessonservice.dto.TypeLesson.TypeLessonDto;

import java.util.List;

public interface TypeOfLessonService {

    TypeLessonDto getTypeLessonById(Long id);

    List<TypeLessonDto> getTypeLessons();

    void saveTypeLesson(TypeLessonDto typeLessonDto);

    void updateTypeLesson(TypeLessonDto typeLessonDto);

    void deleteTypeLessonById(Long id);
}
