package com.example.profileservice.service.impl;

import com.example.profileservice.dto.student.StudentCreateDto;
import com.example.profileservice.dto.student.StudentDto;
import com.example.profileservice.dto.student.StudentUpdateDto;
import com.example.profileservice.entity.Student;
import com.example.profileservice.exception.NotFoundException;
import com.example.profileservice.mapper.StudentMapper;
import com.example.profileservice.repository.StudentRepository;
import com.example.profileservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Override
    public StudentDto getStudentById(Long id) {
        log.info("Выдача студента с id {}", id);
        return studentMapper.studentToStudentDto(studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Студент не был найден")));
    }

    @Override
    public void saveStudent(StudentCreateDto studentCreateDto) {
        log.info("Сохранение студента");
        studentRepository.save(studentMapper.studentCreateDtoToStudent(studentCreateDto));
    }

    @Override
    public void deleteStudent(Long id) {
        log.info("Удаление студента с id {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto updateStudent(StudentUpdateDto studentUpdateDto) {
        Student student = studentRepository.findById(studentUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("Студент не был найден"));
        ofNullable(studentUpdateDto.getName()).ifPresent(student::setName);
        ofNullable(studentUpdateDto.getBirthday()).ifPresent(student::setBirthday);
        ofNullable(studentUpdateDto.getGroupId()).ifPresent(student::setGroupId);
        ofNullable(studentUpdateDto.getSurname()).ifPresent(student::setSurname);
        ofNullable(studentUpdateDto.getPatronymic()).ifPresent(student::setPatronymic);
        ofNullable(studentUpdateDto.getEmail()).ifPresent(student::setEmail);
        return studentMapper.studentToStudentDto(studentRepository.save(student));
    }

    @Override
    public List<StudentDto> getStudentByGroupId(Long id) {
        log.info("Выдача студентов по группе с id {}", id);
        return studentRepository.getStudentsByGroupId(id)
                .stream().map(studentMapper::studentToStudentDto).toList();
    }
}
