package com.example.demo.Service.Impl;

import com.example.demo.DTO.LessonDto;
import com.example.demo.Entity.*;
import com.example.demo.Mappers.LessonMapper;
import com.example.demo.Repositories.LessonGroupRepository;
import com.example.demo.Repositories.LessonRepository;
import com.example.demo.SaveFromFile.LessonServiceSave;
import com.example.demo.SaveFromFile.NativeLesson;
import com.example.demo.Service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final LessonGroupRepository lessonGroupRepository;

    private final LessonMapper lessonMapper;

    private final LessonServiceSave lessonServiceSave;

    private final LessonFindService lessonFindService;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public LessonDto saveLesson(LessonDto lessonDto, Long weekId, String day) {
        log.info("Сохранение урока");
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        Week week = lessonFindService.getWeekByNumber(weekId);
        fillLesson(lessonDto, lesson);
        lessonFindService.setTimeLesson(lesson.getNumber(), lesson);
        lessonFindService.setDayInWeek(day, week, lesson);
        return lessonMapper.lessonToLessonDto(lessonRepository.save(lesson));
    }

    @Override
    public LessonDto updateLesson(LessonDto lessonDto) {
        log.info("Изменение урока с id {}", lessonDto.getId());
        Lesson lesson = lessonRepository.getLessonById(lessonDto.getId());
        fillLesson(lessonDto, lesson);
        return lessonMapper.lessonToLessonDto(lessonRepository.save(lesson));
    }


    @Override
    public List<LessonDto> getLessonForTeacher(Long id, LocalDateTime day, LocalDateTime day2) {
        log.info("Поиск уроков для преподователя с id {}", id);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        Join<LessonGroup, Group> join1 = root.join(LessonGroup_.GROUP);
        Join<Lesson, Teacher> join2 = join.join(Lesson_.TEACHER);
        Join<Lesson, ClassRoom> join3 = join.join(Lesson_.CLASS_ROOM);
        Join<Lesson, TypeOfLesson> join4 = join.join(Lesson_.TYPE_OF_LESSON);
        Join<Lesson, Courses> join5 = join.join(Lesson_.COURSES);
        cq.where(cb.and(cb.equal(join2.get(Teacher_.ID), id), cb.between(join.get(Lesson_.DAY), day, day2)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)), cb.asc(join.get(Lesson_.COURSES)), cb.asc(join.get(Lesson_.NUMBER)));
        cq.multiselect(
                join.get(Lesson_.ID),
                join5.get(LessonName_.NAME),
                join.get(Lesson_.DAY),
                join.get(Lesson_.FROM_TIME),
                join.get(Lesson_.TO_TIME),
                join.get(Lesson_.NUMBER),
                join2.get(Teacher_.TEACHER_NAME),
                join3.get(ClassRoom_.CLASS_ROOM),
                join1.get(Group_.NAME),
                join4.get(Type_.TYPE)
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<LessonDto> getLesson(Long groupId, LocalDate day , LocalDate day2) {
        log.info("Поиск уроков для группы с id {}", groupId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        Join<Lesson, Teacher> join2 = join.join(Lesson_.TEACHER);
        Join<Lesson, ClassRoom> join3 = join.join(Lesson_.CLASS_ROOM);
        Join<Lesson, TypeOfLesson> join4 = join.join(Lesson_.TYPE_OF_LESSON);
        Join<Lesson, Courses> join5 = join.join(Lesson_.COURSES);
        cq.where(cb.and(cb.equal(root.get(LessonGroup_.GROUP_ID), groupId), cb.between(join.get(Lesson_.DAY), day, day2)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)), cb.asc(join.get(Lesson_.COURSES)), cb.asc(join.get(Lesson_.NUMBER)));
        cq.multiselect(
                join.get(Lesson_.ID),
                join5.get(LessonName_.NAME),
                join.get(Lesson_.DAY),
                join.get(Lesson_.FROM_TIME),
                join.get(Lesson_.TO_TIME),
                join.get(Lesson_.NUMBER),
                join2.get(Teacher_.TEACHER_NAME),
                join3.get(ClassRoom_.CLASS_ROOM),
                join4.get(Type_.TYPE)
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Map<LessonGroup, String> saveLessonFromFile(MultipartFile file) throws IOException {
        log.info("Сохранение уроков из файла");
        List<NativeLesson> nativeLessons = lessonServiceSave.getNativeLesson(file.getInputStream(), 4);
        Map<LessonGroup, String> lessonGroupStringMap = new HashMap<>();
        nativeLessons.stream().parallel().forEach(les -> {
            Week week = lessonFindService.getWeekByNumber(les.getWeak());
            if (week != null) {
                Lesson lesson = new Lesson();
                try {
                    lesson.setCoursesId(lessonFindService.getCourseByNameAndNumberOfCourse(
                            lessonFindService.getGroupByName(les.getGroup()).getNumberCourse(),
                            les.getLesson()
                    ).getId());

                    lesson.setNumber(les.getNumber());

                    lesson.setTeacherId(lessonFindService.getTeacherByName(les.getTeacher()
                            .replace("-- продолжение --", "")
                            .trim()).getId());

                    lesson.setClassRoomId(lessonFindService.getClassRoomByName(les.getClassroom().trim()).getId());

                    lesson.setTypeLessonId(lessonFindService.getTypeOfLessonByName(les.getType().trim()).getId());

                    lessonFindService.setDayInWeek(les.getDay(), week, lesson);
                    lessonFindService.setTimeLesson(les.getNumber(), lesson);
                    Lesson savedLesson = lessonRepository.save(lesson);
                    LessonGroup lessonGroup = new LessonGroup();
                    lessonGroup.setLessonId(savedLesson.getId());
                    lessonGroupStringMap.put(lessonGroup, les.getGroup().trim());
                } catch (Exception ex) {
                    log.info(ex.getMessage());
                }
            }
        });
        return lessonGroupStringMap;
    }

    @Override
    public List<LessonDto> getUpdateLesson(Long groupId, String nameLesson, String type) {
        log.info("Выдача уроков для изменения для группы с id {}", groupId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        Join<Lesson, Teacher> join2 = join.join(Lesson_.TEACHER);
        Join<Lesson, ClassRoom> join3 = join.join(Lesson_.CLASS_ROOM);
        Join<Lesson, TypeOfLesson> join4 = join.join(Lesson_.TYPE_OF_LESSON);
        Join<Lesson, Courses> join5 = join.join(Lesson_.COURSES);
        cq.where(cb.and(cb.equal(root.get(LessonGroup_.GROUP_ID), groupId),
                cb.equal(join5.get(LessonName_.NAME), nameLesson),
                cb.equal(join4.get(Type_.TYPE), type)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)));
        cq.multiselect(
                join.get(Lesson_.ID),
                join5.get(LessonName_.NAME),
                join.get(Lesson_.DAY),
                join.get(Lesson_.FROM_TIME),
                join.get(Lesson_.TO_TIME),
                join.get(Lesson_.NUMBER),
                join2.get(Teacher_.TEACHER_NAME),
                join3.get(ClassRoom_.CLASS_ROOM),
                join4.get(Type_.TYPE)
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    @Transactional
    public void deleteLesson(Long id) {
        log.info("Удаление урока с id {}", id);
        lessonGroupRepository.deleteByLessonId(id);
        lessonRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteLessons(List<Long> ids) {
        ids.forEach(i -> {
            lessonGroupRepository.deleteByLessonId(i);
            lessonRepository.deleteById(i);
        });
    }

    @Override
    public void saveLessonGroup(LessonGroup lessonGroup) {
        log.info("Сохранение уроков для групп");
        lessonGroupRepository.save(lessonGroup);
    }

    @Override
    public LessonDto getLessonById(Long id) {
        log.info("Выдача урока с id {}", id);
        return lessonMapper.lessonToLessonDto(lessonRepository.getLessonById(id));
    }

    private void fillLesson(LessonDto lessonDto, Lesson lesson) {
        lesson.setCoursesId(lessonFindService.getCourseByNameAndNumberOfCourse(
                lessonFindService.getGroupByName(lessonDto.getGroup()).getNumberCourse(),
                lessonDto.getLesson()
        ).getId());

        lesson.setNumber(lessonDto.getNumber());

        lesson.setTeacherId(lessonFindService.getTeacherByName(lessonDto.getTeacherName()
                .replace("-- продолжение --", "")
                .trim()).getId());

        lesson.setClassRoomId(lessonFindService.getClassRoomByName(lessonDto.getClassRoom().trim()).getId());

        lesson.setTypeLessonId(lessonFindService.getTypeOfLessonByName(lessonDto.getType().trim()).getId());
    }
}
