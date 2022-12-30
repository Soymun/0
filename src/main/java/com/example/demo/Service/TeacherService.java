package com.example.demo.Service;

import com.example.demo.DTO.TeacherDto;

import java.util.List;

public interface TeacherService {

    TeacherDto getTeacher(Long id);

    List<TeacherDto> getListTeacher();

    TeacherDto updateTeacher(TeacherDto teacherDto);

    void deleteTeacher(Long id);
}
