package com.example.profileservice.mapper;

import com.example.profileservice.dto.teacher.TeacherCreateDto;
import com.example.profileservice.dto.teacher.TeacherDto;
import com.example.profileservice.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher teacherCreateDtoToTeacher(TeacherCreateDto teacherDto);

    TeacherDto teacherToTeacherDto(Teacher teacher);
}
