package com.example.courseservice.mappers;

import com.example.courseservice.dto.geoupcourse.GroupCourseCreateDto;
import com.example.courseservice.entity.GroupCourse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupCourseMapper {

    GroupCourse groupCourseCreateDtoToGroupCourse(GroupCourseCreateDto groupCourseCreateDto);
}
