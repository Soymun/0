package com.example.demo.Mappers;

import com.example.demo.DTO.Lesson.LessonDto;
import com.example.demo.Entity.ClassRoom;
import com.example.demo.Entity.Courses;
import com.example.demo.Entity.Lesson;
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

    default String map5(ClassRoom value){
        return value == null ? null : value.getClassRoom();
    }
}
