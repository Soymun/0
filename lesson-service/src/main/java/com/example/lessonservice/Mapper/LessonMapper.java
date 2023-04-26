package com.example.lessonservice.Mapper;


import com.example.lessonservice.dto.Lesson.LessonCreateDto;
import com.example.lessonservice.dto.Lesson.LessonDto;
import com.example.lessonservice.entity.ClassRoom;
import com.example.lessonservice.entity.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson lessonCreateDtoToLesson(LessonCreateDto lessonDto);

    LessonDto lessonToLessonDto(Lesson lesson);
}
