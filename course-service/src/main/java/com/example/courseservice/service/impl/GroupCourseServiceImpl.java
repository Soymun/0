package com.example.courseservice.service.impl;

import com.example.courseservice.dto.geoupcourse.GroupCourseCreateDto;
import com.example.courseservice.mappers.GroupCourseMapper;
import com.example.courseservice.repositories.GroupCourseRepository;
import com.example.courseservice.service.GroupCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupCourseServiceImpl implements GroupCourseService {

    private final GroupCourseRepository groupCourseRepository;
    private final GroupCourseMapper groupCourseMapper;

    @Override
    public void saveGroupCourse(GroupCourseCreateDto groupCourseCreateDto) {
        log.info("Сохранение курса группы");
        groupCourseRepository.save(groupCourseMapper.groupCourseCreateDtoToGroupCourse(groupCourseCreateDto));
    }

    @Override
    public void deleteGroupCourse(Long id) {
        log.info("Удаление курса группы с id={}", id);
        groupCourseRepository.deleteById(id);
    }
}
