package com.example.profileservice.mapper;

import com.example.profileservice.dto.student.StudentCreateDto;
import com.example.profileservice.dto.student.StudentDto;
import com.example.profileservice.entity.Student;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto studentToStudentDto(Student student);

    Student studentCreateDtoToStudent(StudentCreateDto studentCreateDto);
}
