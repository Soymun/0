package com.example.demo.Service;

import com.example.demo.DTO.Teacher.TeacherCreateDto;
import com.example.demo.DTO.Teacher.TeacherDto;

import java.util.List;

public interface TeacherService {

    void saveTeacher(TeacherCreateDto teacherCreateDto);

    TeacherDto getTeacher(Long id);

    List<TeacherDto> getListTeacher();

    TeacherDto updateTeacher(TeacherDto teacherDto);

    void deleteTeacher(Long id);
}
