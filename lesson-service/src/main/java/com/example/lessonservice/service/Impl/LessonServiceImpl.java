package com.example.lessonservice.service.Impl;


import com.example.lessonservice.Mapper.LessonMapper;
import com.example.lessonservice.SaveFromFile.LessonServiceSave;
import com.example.lessonservice.SaveFromFile.NativeLesson;
import com.example.lessonservice.dto.Course;
import com.example.lessonservice.dto.Lesson.LessonCreateDto;
import com.example.lessonservice.dto.Lesson.LessonDto;
import com.example.lessonservice.dto.Lesson.LessonToUpdateDto;
import com.example.lessonservice.entity.*;
import com.example.lessonservice.repositories.LessonGroupRepository;
import com.example.lessonservice.repositories.LessonRepository;
import com.example.lessonservice.service.LessonService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private final RestServiceTemplate restServiceTemplate;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Long saveLesson(LessonCreateDto lessonDto, Long weekId, String day) {
        log.info("Сохранение урока");
        Lesson lesson = lessonMapper.lessonCreateDtoToLesson(lessonDto);
        Week week = lessonFindService.getWeekByNumber(weekId);
        lessonFindService.setTimeLesson(lesson.getNumber(), lesson);
        lessonFindService.setDayInWeek(day, week, lesson);
        return lessonRepository.save(lesson).getId();
    }

    @Override
    public void updateLesson(LessonToUpdateDto lessonToUpdateDto) {
        lessonToUpdateDto.getIds().stream().parallel().forEach(
                n -> {
                    log.info("Изменение урока с id {}", n);
                    Lesson lesson = lessonRepository.getLessonById(n);
                    if(lesson != null) {
                        if (lessonToUpdateDto.getCourseId() != null) {
                            lesson.setCoursesId(lessonToUpdateDto.getCourseId());
                        }
                        if (lessonToUpdateDto.getTeacherId() != null) {
                            lesson.setTeacherId(lessonToUpdateDto.getTeacherId());
                        }
                        if (lessonToUpdateDto.getClassRoomId() != null) {
                            lesson.setClassRoomId(lessonToUpdateDto.getClassRoomId());
                        }
                        if (lessonToUpdateDto.getDayOfWeek() != null && !lessonToUpdateDto.getDayOfWeek().getDayOfWeek().equals(lesson.getDay().getDayOfWeek())) {
                            int range = lessonToUpdateDto.getDayOfWeek().getDayOfWeek().compareTo(lesson.getDay().getDayOfWeek());
                            lesson.setDay(lessonToUpdateDto.getDayOfWeek().plusDays(range));
                        }
                        if (lessonToUpdateDto.getFromTime() != null) {
                            lesson.setFromTime(lesson.getFromTime().withHour(lessonToUpdateDto.getFromTime().getHour()).withMinute(lessonToUpdateDto.getFromTime().getMinute()));
                        }
                        if (lessonToUpdateDto.getToTime() != null) {
                            lesson.setToTime(lesson.getToTime().withHour(lessonToUpdateDto.getToTime().getHour()).withMinute(lessonToUpdateDto.getToTime().getMinute()));

                        }
                        if (lessonToUpdateDto.getTypeId() != null) {
                            lesson.setTeacherId(lessonToUpdateDto.getTypeId());
                        }
                        lessonRepository.save(lesson);
                    }
                }
        );
    }


    @Override
    public List<LessonDto> getLessonForTeacher(Long id, LocalDateTime day, LocalDateTime day2) {
        log.info("Поиск уроков для преподователя с id {}", id);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        Subquery<ClassRoom> subquery = cq.subquery(ClassRoom.class);
        Root<ClassRoom> roomRoot = subquery.from(ClassRoom.class);
        subquery.select(roomRoot);

        Subquery<TypeOfLesson> subqueryType = cq.subquery(TypeOfLesson.class);
        Root<TypeOfLesson> typeOfLessonRoot = subqueryType.from(TypeOfLesson.class);
        subqueryType.select(typeOfLessonRoot);
        cq.where(cb.and(cb.equal(join.get(Lesson_.TEACHER_ID), id), cb.between(join.get(Lesson_.DAY), day, day2)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)),cb.asc(join.get(Lesson_.COURSES_ID)), cb.asc(join.get(Lesson_.NUMBER)));
        cq.multiselect(
                join.get(Lesson_.ID),
                join.get(Lesson_.COURSES_ID),
                join.get(Lesson_.DAY),
                join.get(Lesson_.FROM_TIME),
                join.get(Lesson_.TO_TIME),
                join.get(Lesson_.NUMBER),
                join.get(Lesson_.TEACHER_ID),
                subquery,
                subqueryType,
                root.get(LessonGroup_.GROUP_ID)
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

        Subquery<ClassRoom> subquery = cq.subquery(ClassRoom.class);
        Root<ClassRoom> roomRoot = subquery.from(ClassRoom.class);
        subquery.select(roomRoot);

        Subquery<TypeOfLesson> subqueryType = cq.subquery(TypeOfLesson.class);
        Root<TypeOfLesson> typeOfLessonRoot = subqueryType.from(TypeOfLesson.class);
        subqueryType.select(typeOfLessonRoot);

        cq.where(cb.and(cb.equal(root.get(LessonGroup_.GROUP_ID), groupId), cb.between(join.get(Lesson_.DAY), day, day2)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)),cb.asc(join.get(Lesson_.COURSES_ID)), cb.asc(join.get(Lesson_.NUMBER)));
        cq.multiselect(
                join.get(Lesson_.ID),
                join.get(Lesson_.COURSES_ID),
                join.get(Lesson_.DAY),
                join.get(Lesson_.FROM_TIME),
                join.get(Lesson_.TO_TIME),
                join.get(Lesson_.NUMBER),
                join.get(Lesson_.TEACHER_ID),
                subquery,
                subqueryType,
                root.get(LessonGroup_.GROUP_ID)
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
                    fillLesson(lesson, les.getNumber(), les.getTeacher(), les.getGroup(), les.getLesson(), les.getClassroom(), les.getType());
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
    public List<LessonDto> getUpdateLesson(Long groupId, Long coursesId, Long typeId) {
        log.info("Выдача уроков для изменения для группы с id {}", groupId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        Subquery<ClassRoom> subquery = cq.subquery(ClassRoom.class);
        Root<ClassRoom> roomRoot = subquery.from(ClassRoom.class);
        subquery.select(roomRoot);

        Subquery<TypeOfLesson> subqueryType = cq.subquery(TypeOfLesson.class);
        Root<TypeOfLesson> typeOfLessonRoot = subqueryType.from(TypeOfLesson.class);
        subqueryType.select(typeOfLessonRoot);
        cq.where(cb.and(cb.equal(root.get(LessonGroup_.GROUP_ID), groupId),
                cb.equal(join.get(Lesson_.COURSES_ID), coursesId),
                cb.equal(join.get(Lesson_.TYPE_LESSON_ID), typeId)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)));
        cq.multiselect(
                join.get(Lesson_.ID),
                join.get(Lesson_.COURSES_ID),
                join.get(Lesson_.DAY),
                join.get(Lesson_.FROM_TIME),
                join.get(Lesson_.TO_TIME),
                join.get(Lesson_.NUMBER),
                join.get(Lesson_.TEACHER_ID),
                subquery,
                subqueryType,
                root.get(LessonGroup_.GROUP_ID)
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

    private void fillLesson(Lesson lesson,
                            Long number,
                            String teacherName,
                            String group,
                            String nameLesson,
                            String classRoom,
                            String type) {
        Course course = restServiceTemplate.getCourse(group, nameLesson);
        lesson.setCoursesId(course.getId());

        lesson.setNumber(number);

        lesson.setTeacherId(restServiceTemplate.getTeacher(teacherName
                .replace("-- продолжение --", "")
                .trim()).getId());

        lesson.setClassRoomId(lessonFindService.getClassRoomByName(classRoom.trim()).getId());

        lesson.setTypeLessonId(lessonFindService.getTypeOfLessonByName(type.trim()).getId());
    }
}
