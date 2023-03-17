package com.example.demo.Mappers;

import com.example.demo.DTO.Teacher.TeacherCreateDto;
import com.example.demo.DTO.Teacher.TeacherDto;
import com.example.demo.Entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher teacherCreateDtoToTeacher(TeacherCreateDto teacherDto);

    TeacherDto teacherToTeacherDto(Teacher teacher);
}
