package com.example.profileservice.service;

import com.example.profileservice.dto.student.StudentCreateDto;
import com.example.profileservice.dto.student.StudentDto;
import com.example.profileservice.dto.student.StudentUpdateDto;

import java.util.List;

public interface StudentService {

    StudentDto getStudentById(Long id);

    void saveStudent(StudentCreateDto studentCreateDto);

    void deleteStudent(Long id);

    StudentDto updateStudent(StudentUpdateDto studentUpdateDto);

    List<StudentDto> getStudentByGroupId(Long id);
}
