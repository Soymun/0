package com.example.profileservice.service;

import com.example.profileservice.dto.teacher.TeacherCreateDto;
import com.example.profileservice.dto.teacher.TeacherDto;
import com.example.profileservice.dto.teacher.TeacherUpdateDto;

import java.util.List;

public interface TeacherService {

    void saveTeacher(TeacherCreateDto teacherCreateDto);

    TeacherDto getTeacher(Long id);

    List<TeacherDto> getListTeacherByUniversityId(Long universityId);

    TeacherDto updateTeacher(TeacherUpdateDto teacherDto);

    TeacherDto getTeacherByNameAndUniversityId(Long universityId, String name);

    void deleteTeacher(Long id);
}
