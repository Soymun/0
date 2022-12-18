package com.example.demo.Service.Impl;

import com.example.demo.DTO.LessonDto;
import com.example.demo.Entity.*;
import com.example.demo.Mappers.LessonMapper;
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

    private final LessonMapper lessonMapper;

    private final LessonServiceSave lessonServiceSave;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, WeekRepository weekRepository, LessonMapper lessonMapper, LessonServiceSave lessonServiceSave) {
        this.lessonRepository = lessonRepository;
        this.weekRepository = weekRepository;
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
    public List<LessonDto> getLesson(Long groupId, LocalDateTime day, LocalDateTime day2) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);

        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        cq.where(cb.and(cb.equal(root.get(LessonGroup_.GROUP_ID),groupId), cb.between(join.get(Lesson_.DAY), day, day2)));

        cq.multiselect(
                join.get(Lesson_.ID),
                join.get(Lesson_.LESSON),
                join.get(Lesson_.DAY),
                join.get(Lesson_.NUMBER),
                join.get(Lesson_.TEACHER_NAME),
                join.get(Lesson_.CLASS_ROOM)
        );

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Map<LessonGroup, String> saveLessonFromFile(MultipartFile file) throws IOException {
        List<NativeLesson> nativeLessons = lessonServiceSave.getNativeLesson(file.getInputStream());
        Map<LessonGroup, String> lessonGroupStringMap = new HashMap<>();
        for (NativeLesson nativeLesson: nativeLessons){
            Week week = weekRepository.findWeekById(nativeLesson.getWeak());
            if(week != null){
                Lesson lesson = new Lesson();
                lesson.setLesson(nativeLesson.getLesson());
                lesson.setNumber(nativeLesson.getNumber());
                lesson.setTeacherName(nativeLesson.getTeacher());
                lesson.setClassRoom(nativeLesson.getClassroom());
                switch (nativeLesson.getDay()){
                    case "Monday" -> lesson.setDay(week.getFrom());
                    case "Tuesday" -> lesson.setDay(week.getFrom().plusDays(1));
                    case "Wednesday" -> lesson.setDay(week.getFrom().plusDays(2));
                    case "Thursday" -> lesson.setDay(week.getFrom().plusDays(3));
                    case "Friday" -> lesson.setDay(week.getFrom().plusDays(4));
                    case "Saturday" -> lesson.setDay(week.getFrom().plusDays(5));
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
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public void deleteLesson(List<Long> ids) {
        ids.forEach(lessonRepository::deleteById);
    }
}
