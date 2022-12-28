package com.example.demo.Mappers;

import com.example.demo.DTO.LessonDto;
import com.example.demo.Entity.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson lessonDtoToLesson(LessonDto lessonDto);

    LessonDto lessonToLessonDto(Lesson lesson);

    default LessonName map(String value){
        return new LessonName(value);
    }

    default ClassRoom map2(String value){
        return new ClassRoom(value);
    }
    default Type map3(String value){
        return new Type(value);
    }

    default String map4(LessonName value){
        return value == null ? null : value.getName();
    }

    default String map5(ClassRoom value){
        return value == null ? null : value.getClassRoom();
    }

    default String map6(Type value){
        return value == null ? null : value.getType();
    }
}
