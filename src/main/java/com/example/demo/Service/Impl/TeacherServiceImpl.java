package com.example.demo.Service.Impl;

import com.example.demo.DTO.TeacherDto;
import com.example.demo.Entity.Teacher;
import com.example.demo.Mappers.TeacherMapper;
import com.example.demo.Repositories.TeacherRepository;
import com.example.demo.Service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherDto getTeacher(Long id) {
        return teacherMapper.teacherToTeacherDto(teacherRepository.getTeacherById(id));
    }

    @Override
    public List<TeacherDto> getListTeacher() {
        return teacherRepository.findAll().stream().map(teacherMapper::teacherToTeacherDto).toList();
    }

    @Override
    public TeacherDto updateTeacher(TeacherDto teacherDto) {
        Teacher teacher = teacherRepository.getTeacherById(teacherDto.getId());
        if(teacherDto.getTeacherName() != null){
            teacher.setTeacherName(teacherDto.getTeacherName());
        }
        return teacherMapper.teacherToTeacherDto(teacherRepository.save(teacher));
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
