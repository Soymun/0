package com.example.lessonservice.Mapper;


import com.example.lessonservice.dto.Lesson.LessonDto;
import com.example.lessonservice.entity.ClassRoom;
import com.example.lessonservice.entity.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson lessonDtoToLesson(LessonDto lessonDto);

    LessonDto lessonToLessonDto(Lesson lesson);

    default ClassRoom map2(String value){
        return new ClassRoom(value);
    }

    default String map5(ClassRoom value){
        return value == null ? null : value.getClassRoom();
    }
}
