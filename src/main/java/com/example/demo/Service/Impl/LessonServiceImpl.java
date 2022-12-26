package com.example.demo.Service.Impl;

import com.example.demo.DTO.LessonDto;
import com.example.demo.DTO.TeacherLessonDto;
import com.example.demo.Entity.*;
import com.example.demo.Entity.Group_;
import com.example.demo.Entity.LessonGroup_;
import com.example.demo.Entity.Lesson_;
import com.example.demo.Mappers.LessonMapper;
import com.example.demo.Repositories.LessonGroupRepository;
import com.example.demo.Repositories.LessonRepository;
import com.example.demo.Repositories.WeekRepository;
import com.example.demo.SaveFromFile.LessonServiceSave;
import com.example.demo.SaveFromFile.NativeLesson;
import com.example.demo.Service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final WeekRepository weekRepository;

    private final LessonGroupRepository lessonGroupRepository;

    private final LessonMapper lessonMapper;

    private final LessonServiceSave lessonServiceSave;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, WeekRepository weekRepository, LessonGroupRepository lessonGroupRepository, LessonMapper lessonMapper, LessonServiceSave lessonServiceSave) {
        this.lessonRepository = lessonRepository;
        this.weekRepository = weekRepository;
        this.lessonGroupRepository = lessonGroupRepository;
        this.lessonMapper = lessonMapper;
        this.lessonServiceSave = lessonServiceSave;
    }

    @Override
    public LessonDto saveLesson(LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        return lessonMapper.lessonToLessonDto(lessonRepository.save(lesson));
    }

    @Override
    public LessonDto updateLesson(LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        return lessonMapper.lessonToLessonDto(lessonRepository.save(lesson));
    }

    @Override
    public List<TeacherLessonDto> getLessonForTeacher(String teacher, LocalDateTime day, LocalDateTime day2) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeacherLessonDto> cq = cb.createQuery(TeacherLessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        Join<LessonGroup, Group> join1 = root.join(LessonGroup_.GROUP);
        cq.where(cb.and(cb.equal(join.get(Lesson_.TEACHER_NAME), teacher), cb.between(join.get(Lesson_.DAY), day, day2)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)),cb.asc(join.get(Lesson_.LESSON)),cb.asc(join.get(Lesson_.NUMBER)));
        cq.multiselect(
                join.get(Lesson_.ID),
                join.get(Lesson_.LESSON),
                join.get(Lesson_.DAY),
                join.get(Lesson_.FROM_TIME),
                join.get(Lesson_.TO_TIME),
                join.get(Lesson_.NUMBER),
                join.get(Lesson_.TEACHER_NAME),
                join.get(Lesson_.CLASS_ROOM),
                join1.get(Group_.NAME),
                join.get(Lesson_.TYPE)
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<LessonDto> getLesson(Long groupId, LocalDateTime day, LocalDateTime day2) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        cq.where(cb.and(cb.equal(root.get(LessonGroup_.GROUP_ID),groupId), cb.between(join.get(Lesson_.DAY), day, day2)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)),cb.asc(join.get(Lesson_.LESSON)),cb.asc(join.get(Lesson_.NUMBER)));
        cq.multiselect(
                join.get(Lesson_.ID),
                join.get(Lesson_.LESSON),
                join.get(Lesson_.DAY),
                join.get(Lesson_.FROM_TIME),
                join.get(Lesson_.TO_TIME),
                join.get(Lesson_.NUMBER),
                join.get(Lesson_.TEACHER_NAME),
                join.get(Lesson_.CLASS_ROOM),
                join.get(Lesson_.TYPE)
        );

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Map<LessonGroup, String> saveLessonFromFile(MultipartFile file) throws IOException {
        List<NativeLesson> nativeLessons = lessonServiceSave.getNativeLesson(file.getInputStream()).stream().distinct().toList();
        Map<LessonGroup, String> lessonGroupStringMap = new HashMap<>();
        for (NativeLesson nativeLesson: nativeLessons){
            Week week = weekRepository.findWeekById(nativeLesson.getWeak());
            if(week != null){
                Lesson lesson = new Lesson();
                lesson.setLesson(nativeLesson.getLesson());
                lesson.setNumber(nativeLesson.getNumber());
                lesson.setTeacherName(nativeLesson.getTeacher());
                lesson.setClassRoom(nativeLesson.getClassroom());
                lesson.setType(nativeLesson.getType());
                switch (nativeLesson.getDay()){
                    case "ПОНЕДЕЛЬНИК" -> lesson.setDay(week.getFromWeek());
                    case "ВТОРНИК" -> lesson.setDay(week.getFromWeek().plusDays(1));
                    case "СРЕДА" -> lesson.setDay(week.getFromWeek().plusDays(2));
                    case "ЧЕТВЕРГ" -> lesson.setDay(week.getFromWeek().plusDays(3));
                    case "ПЯТНИЦА" -> lesson.setDay(week.getFromWeek().plusDays(4));
                    case "СУББОТА" -> lesson.setDay(week.getFromWeek().plusDays(5));
                }
                switch (lesson.getNumber().intValue()){
                    case 1 -> {
                        lesson.setFromTime(lesson.getDay().plusHours(8).plusMinutes(30));
                        lesson.setToTime(lesson.getDay().plusHours(10).plusMinutes(0));
                    }
                    case 2 -> {
                        lesson.setFromTime(lesson.getDay().plusHours(10).plusMinutes(10));
                        lesson.setToTime(lesson.getDay().plusHours(11).plusMinutes(40));
                    }
                    case 3 -> {
                        lesson.setFromTime(lesson.getDay().plusHours(11).plusMinutes(50));
                        lesson.setToTime(lesson.getDay().plusHours(13).plusMinutes(20));
                    }
                    case 4 -> {
                        lesson.setFromTime(lesson.getDay().plusHours(12).plusMinutes(20));
                        lesson.setToTime(lesson.getDay().plusHours(13).plusMinutes(50));
                    }
                    case 5 -> {
                        lesson.setFromTime(lesson.getDay().plusHours(14).plusMinutes(0));
                        lesson.setToTime(lesson.getDay().plusHours(15).plusMinutes(30));
                    }
                    case 6 -> {
                        lesson.setFromTime(lesson.getDay().plusHours(15).plusMinutes(40));
                        lesson.setToTime(lesson.getDay().plusHours(17).plusMinutes(10));
                    }
                    case 7 -> {
                        lesson.setFromTime(lesson.getDay().plusHours(17).plusMinutes(30));
                        lesson.setToTime(lesson.getDay().plusHours(19).plusMinutes(0));
                    }
                }
                Lesson savedLesson = lessonRepository.save(lesson);
                LessonGroup lessonGroup = new LessonGroup();
                lessonGroup.setLessonId(savedLesson.getId());
                lessonGroupStringMap.put(lessonGroup, nativeLesson.getGroup().trim());
            }
        }
        return lessonGroupStringMap;
    }

    @Override
    public List<LessonDto> getUpdateLesson(Long groupId, String nameLesson, String type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        cq.where(cb.and(cb.equal(root.get(LessonGroup_.GROUP_ID),groupId),
                cb.equal(join.get(Lesson_.LESSON), nameLesson),
                cb.equal(join.get(Lesson_.TYPE), type)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)));
        cq.multiselect(
                join.get(Lesson_.ID),
                join.get(Lesson_.LESSON),
                join.get(Lesson_.DAY),
                join.get(Lesson_.FROM_TIME),
                join.get(Lesson_.TO_TIME),
                join.get(Lesson_.NUMBER),
                join.get(Lesson_.TEACHER_NAME),
                join.get(Lesson_.CLASS_ROOM),
                join.get(Lesson_.TYPE)
        );

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public void deleteLesson(Long id) {
        lessonGroupRepository.deleteByLessonId(id);
        lessonRepository.deleteById(id);
    }

    @Override
    public void deleteLesson(List<Long> ids) {
        ids.forEach(lessonGroupRepository::deleteByLessonId);
        ids.forEach(lessonRepository::deleteById);
    }

    @Override
    public void saveLessonGroup(LessonGroup lessonGroup) {
        lessonGroupRepository.save(lessonGroup);
    }

    @Override
    public LessonDto getLessonById(Long id) {
        return lessonMapper.lessonToLessonDto(lessonRepository.getLessonById(id));
    }


}
