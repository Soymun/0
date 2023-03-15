package com.example.demo.Mappers;

import com.example.demo.DTO.LessonDto;
import com.example.demo.Entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson lessonDtoToLesson(LessonDto lessonDto);

    LessonDto lessonToLessonDto(Lesson lesson);

    default Courses map(String value){
        return new Courses(value);
    }

    default ClassRoom map2(String value){
        return new ClassRoom(value);
    }
    default TypeOfLesson map3(String value){
        return new TypeOfLesson(value);
    }

    default String map4(Courses value){
        return value == null ? null : value.getName();
    }

    default String map5(ClassRoom value){
        return value == null ? null : value.getClassRoom();
    }

    default String map6(TypeOfLesson value){
        return value == null ? null : value.getType();
    }
}
