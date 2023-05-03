package com.example.profileservice.service.impl;

import com.example.profileservice.dto.teacher.TeacherCreateDto;
import com.example.profileservice.dto.teacher.TeacherDto;
import com.example.profileservice.dto.teacher.TeacherUpdateDto;
import com.example.profileservice.entity.Teacher;
import com.example.profileservice.exception.NotFoundException;
import com.example.profileservice.mapper.TeacherMapper;
import com.example.profileservice.repository.TeacherRepository;
import com.example.profileservice.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

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
        return teacherMapper.teacherToTeacherDto(teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Учитель не найден")));
    }

    @Override
    public List<TeacherDto> getListTeacherByUniversityId(Long universityId) {
        log.info("Выдача всех учителей");
        return teacherRepository.getTeachersByUniversityId(universityId).stream().map(teacherMapper::teacherToTeacherDto).toList();
    }

    @Override
    public TeacherDto updateTeacher(TeacherUpdateDto teacherDto) {
        log.info("Изменение учителя с id {}", teacherDto.getId());
        Teacher teacher = teacherRepository.findById(teacherDto.getId())
                .orElseThrow(() -> new NotFoundException("Учитель не найден"));
        ofNullable(teacherDto.getNameTeacher()).ifPresent(teacher::setNameTeacher);
        ofNullable(teacherDto.getSurnameTeacher()).ifPresent(teacher::setSurnameTeacher);
        ofNullable(teacherDto.getPatronymicTeacher()).ifPresent(teacher::setPatronymicTeacher);
        ofNullable(teacherDto.getName()).ifPresent(teacher::setName);
        ofNullable(teacherDto.getUniversityId()).ifPresent(teacher::setUniversityId);
        return teacherMapper.teacherToTeacherDto(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDto getTeacherByNameAndUniversityId(Long universityId, String name) {
        log.info("Выдача учителя в университете с id {} и именем {}", universityId, name);
        return teacherMapper.teacherToTeacherDto(teacherRepository.getTeachersByUniversityIdAndName(universityId, name));
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        log.info("Удаление учителя с id {}", id);
        teacherRepository.deleteById(id);
    }
}
