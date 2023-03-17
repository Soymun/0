package com.example.demo.Service.Impl;

import com.example.demo.DTO.Teacher.TeacherCreateDto;
import com.example.demo.DTO.Teacher.TeacherDto;
import com.example.demo.Entity.Teacher;
import com.example.demo.Mappers.TeacherMapper;
import com.example.demo.Repositories.TeacherRepository;
import com.example.demo.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    @Override
    public void saveTeacher(TeacherCreateDto teacherCreateDto) {
        log.info("Создание учителя у пользователя с id {}", teacherCreateDto.getUserId());
        teacherRepository.save(teacherMapper.teacherCreateDtoToTeacher(teacherCreateDto));
    }

    @Override
    public TeacherDto getTeacher(Long id) {
        log.info("Выдача учителя с id {}", id);
        return teacherMapper.teacherToTeacherDto(teacherRepository.getTeacherById(id));
    }

    @Override
    public List<TeacherDto> getListTeacher() {
        log.info("Выдача всех учителей");
        return teacherRepository.findAll().stream().map(teacherMapper::teacherToTeacherDto).toList();
    }

    @Override
    public TeacherDto updateTeacher(TeacherDto teacherDto) {
        log.info("Изменение учителя с id {}", teacherDto.getId());
        Teacher teacher = teacherRepository.getTeacherById(teacherDto.getId());
        if(teacherDto.getTeacherName() != null){
            teacher.setTeacherName(teacherDto.getTeacherName());
        }
        return teacherMapper.teacherToTeacherDto(teacherRepository.save(teacher));
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        log.info("Удаление учителя с id {}", id);
        teacherRepository.deleteById(id);
    }
}
