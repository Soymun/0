package com.example.courseservice.mappers;

import com.example.courseservice.dto.course.CourseCreateDto;
import com.example.courseservice.dto.course.CourseDto;
import com.example.courseservice.entity.Courses;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Courses courseCreateDtoToCourse(CourseCreateDto courseCreateDto);

    CourseDto courseToCourseDto(Courses courses);
}
