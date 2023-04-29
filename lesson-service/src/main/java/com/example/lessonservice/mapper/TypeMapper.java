package com.example.lessonservice.mapper;

import com.example.lessonservice.dto.TypeLesson.TypeLessonDto;
import com.example.lessonservice.entity.TypeOfLesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    TypeLessonDto typeToTypeLessonDto(TypeOfLesson type);

    TypeOfLesson typeLessonDtoToType(TypeLessonDto typeLessonDto);
}
