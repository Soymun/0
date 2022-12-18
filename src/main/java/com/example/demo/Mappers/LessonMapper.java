package com.example.demo.Mappers;

import com.example.demo.DTO.LessonDto;
import com.example.demo.Entity.Group;
import com.example.demo.Entity.Lesson;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson lessonDtoToLesson(LessonDto lessonDto);

    LessonDto lessonToLessonDto(Lesson lesson);

    default List<Long> map(List<Group> groups){
        return groups.stream().map(Group::getId).toList();
    }

    default List<Group> map2(List<Long> groups){
        return groups.stream().map(Group::new).toList();
    }
}
