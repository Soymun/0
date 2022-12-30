package com.example.demo.Mappers;

import com.example.demo.DTO.TeacherDto;
import com.example.demo.Entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher teacherDtoToTeacher(TeacherDto teacherDto);

    TeacherDto teacherToTeacherDto(Teacher teacher);
}
