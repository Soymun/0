package com.example.courseservice.service.impl;

import com.example.courseservice.dto.course.CourseDto;
import com.example.courseservice.dto.geoupcourse.GroupCourseCreateDto;
import com.example.courseservice.entity.Courses;
import com.example.courseservice.entity.Courses_;
import com.example.courseservice.entity.GroupCourse;
import com.example.courseservice.entity.GroupCourse_;
import com.example.courseservice.mappers.GroupCourseMapper;
import com.example.courseservice.repositories.GroupCourseRepository;
import com.example.courseservice.service.GroupCourseService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupCourseServiceImpl implements GroupCourseService {

    private final GroupCourseRepository groupCourseRepository;
    private final GroupCourseMapper groupCourseMapper;

    @PersistenceContext
    EntityManager entityManager;

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

    @Override
    public CourseDto getCourseByGroupIdAndCourseName(Long groupId, String courseName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CourseDto> cq = cb.createQuery(CourseDto.class);
        Root<GroupCourse> root = cq.from(GroupCourse.class);

        Join<GroupCourse, Courses> join = root.join(GroupCourse_.COURSES);

        cq.where(cb.and(cb.equal(root.get(GroupCourse_.groupId), groupId)), cb.equal(join.get(Courses_.name), courseName));

        cq.multiselect(
                join.get(Courses_.id),
                join.get(Courses_.name),
                join.get(Courses_.numberOfCourse),
                join.get(Courses_.teacherId),
                join.get(Courses_.universityId)
        );
        return entityManager.createQuery(cq).getSingleResult();
    }
}
