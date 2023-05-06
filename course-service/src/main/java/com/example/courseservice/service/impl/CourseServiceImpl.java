package com.example.courseservice.service.impl;

import com.example.courseservice.dto.course.CourseCreateDto;
import com.example.courseservice.dto.course.CourseDto;
import com.example.courseservice.dto.course.CourseUpdateDto;
import com.example.courseservice.entity.Courses;
import com.example.courseservice.exception.NotFoundException;
import com.example.courseservice.mappers.CourseMapper;
import com.example.courseservice.repositories.CoursesRepository;
import com.example.courseservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CoursesRepository coursesRepository;

    private final CourseMapper courseMapper;


    @Override
    public void saveCourse(CourseCreateDto courseCreateDto) {
        log.info("Сохранение курса");
        coursesRepository.save(courseMapper.courseCreateDtoToCourse(courseCreateDto));
    }

    @Override
    public CourseDto updateCourse(CourseUpdateDto courseUpdateDto) {
        log.info("Изменение курса с id={}", courseUpdateDto.getId());
        Courses courses = coursesRepository.findById(courseUpdateDto.getId()).orElseThrow(() -> new NotFoundException("Курс не найден"));
        ofNullable(courseUpdateDto.getNumberOfCourse()).ifPresent(courses::setNumberOfCourse);
        ofNullable(courseUpdateDto.getName()).ifPresent(courses::setName);
        ofNullable(courseUpdateDto.getTeacherId()).ifPresent(courses::setTeacherId);
        return courseMapper.courseToCourseDto(coursesRepository.save(courses));
    }

    @Override
    public void deleteCourse(Long id) {
        log.info("Удаление курса с id={}", id);
        coursesRepository.deleteById(id);
    }

    @Override
    public CourseDto getCourseById(Long id) {
        log.info("Выдача курса с id={}", id);
        return courseMapper.courseToCourseDto(coursesRepository.findById(id).orElseThrow(() -> new NotFoundException("Курс не найден")));
    }

    @Override
    public CourseDto getCourseByNameAndNumberCourse(String name, Long numberCourse) {
        log.info("Выдача курса с названием={} и номером курса={}", name, numberCourse);
        return courseMapper.courseToCourseDto(coursesRepository.getCoursesByNumberOfCourseAndName(numberCourse, name).orElseThrow(() -> new NotFoundException("Курс не найден")));
    }

    @Override
    public List<CourseDto> getCourseByUniversityId(Long universityId) {
        log.info("Выдача курсов по университету с id={}", universityId);
        return coursesRepository.getCoursesByUniversityId(universityId)
                .stream()
                .map(courseMapper::courseToCourseDto)
                .toList();
    }
}
