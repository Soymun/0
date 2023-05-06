package com.example.courseservice.service;

import com.example.courseservice.dto.geoupcourse.GroupCourseCreateDto;

public interface GroupCourseService {

    void saveGroupCourse(GroupCourseCreateDto groupCourseCreateDto);

    void deleteGroupCourse(Long id);
}
