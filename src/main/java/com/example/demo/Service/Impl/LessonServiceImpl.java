package com.example.demo.Service.Impl;

import com.example.demo.DTO.LessonDto;
import com.example.demo.Entity.*;
import com.example.demo.Mappers.LessonMapper;
import com.example.demo.Repositories.*;
import com.example.demo.SaveFromFile.LessonServiceSave;
import com.example.demo.SaveFromFile.NativeLesson;
import com.example.demo.Service.LessonService;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {


    private final Map<LessonGroup, String> lessonGroupStringMap = new HashMap<>();
    private final LessonRepository lessonRepository;

    private final WeekRepository weekRepository;

    private final LessonGroupRepository lessonGroupRepository;

    private final LessonMapper lessonMapper;

    private final LessonServiceSave lessonServiceSave;

    private final LessonNameRepository lessonNameRepository;

    private final TeacherRepository teacherRepository;

    private final ClassRoomRepository classRoomRepository;

    private final TypeRepository typeRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public LessonDto saveLesson(LessonDto lessonDto,Long weekId, String day) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        Week week = weekRepository.findWeekById(weekId);
        modifyLesson(lessonDto, lesson);
        getTimeByNumberOfLesson(lesson.getNumber(), lesson, week, day);
        return lessonMapper.lessonToLessonDto(lessonRepository.save(lesson));
    }

    @Override
    public LessonDto updateLesson(LessonDto lessonDto) {
        Lesson lesson = lessonRepository.getLessonById(lessonDto.getId());
        modifyLesson(lessonDto, lesson);
        return lessonMapper.lessonToLessonDto(lessonRepository.save(lesson));
    }

    @Override
    public List<LessonDto> getLessonForTeacher(String teacher, LocalDateTime day, LocalDateTime day2) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        Join<LessonGroup, Group> join1 = root.join(LessonGroup_.GROUP);
        Join<Lesson, Teacher> join2 = join.join(Lesson_.TEACHER);
        Join<Lesson, ClassRoom> join3 = join.join(Lesson_.CLASS_ROOM);
        Join<Lesson, TypeOfLesson> join4 = join.join(Lesson_.TYPE);
        Join<Lesson, Courses> join5 = join.join(Lesson_.LESSON);
        cq.where(cb.and(cb.equal(join2.get(Teacher_.TEACHER_NAME), teacher), cb.between(join.get(Lesson_.DAY), day, day2)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)),cb.asc(join.get(Lesson_.LESSON)),cb.asc(join.get(Lesson_.NUMBER)));
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
    public List<LessonDto> getLesson(Long groupId, LocalDateTime day, LocalDateTime day2) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        Join<Lesson, Teacher> join2 = join.join(Lesson_.TEACHER);
        Join<Lesson, ClassRoom> join3 = join.join(Lesson_.CLASS_ROOM);
        Join<Lesson, TypeOfLesson> join4 = join.join(Lesson_.TYPE);
        Join<Lesson, Courses> join5 = join.join(Lesson_.LESSON);
        cq.where(cb.and(cb.equal(root.get(LessonGroup_.GROUP_ID),groupId), cb.between(join.get(Lesson_.DAY), day, day2)));
        cq.orderBy(cb.asc(join.get(Lesson_.DAY)),cb.asc(join.get(Lesson_.LESSON)),cb.asc(join.get(Lesson_.NUMBER)));
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
    public Map<LessonGroup, String> saveLessonFromFile(MultipartFile file) throws IOException, InterruptedException, ExecutionException {
        List<NativeLesson> nativeLessons = lessonServiceSave.getNativeLesson(file.getInputStream(), 4);
//        nativeLessons.stream(n -> {
//                Week week = weekRepository.findWeekById(n.getWeak());
//                if (week != null) {
//                    Lesson lesson = new Lesson();
//                    modifyLesson(n, lesson);
//                    getTimeByNumberOfLesson(lesson.getNumber(), lesson, week, n.getDay());
//                    Lesson savedLesson = lessonRepository.save(lesson);
//                    LessonGroup lessonGroup = new LessonGroup();
//                    lessonGroup.setLessonId(savedLesson.getId());
//                    lessonGroupStringMap.put(lessonGroup, n.getGroup().trim());
//                }
//        });
        return lessonGroupStringMap;
    }

    @Override
    public List<LessonDto> getUpdateLesson(Long groupId, String nameLesson, String type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> cq = cb.createQuery(LessonDto.class);
        Root<LessonGroup> root = cq.from(LessonGroup.class);
        Join<LessonGroup, Lesson> join = root.join(LessonGroup_.LESSON);
        Join<Lesson, Teacher> join2 = join.join(Lesson_.TEACHER);
        Join<Lesson, ClassRoom> join3 = join.join(Lesson_.CLASS_ROOM);
        Join<Lesson, TypeOfLesson> join4 = join.join(Lesson_.TYPE);
        Join<Lesson, Courses> join5 = join.join(Lesson_.LESSON);
        cq.where(cb.and(cb.equal(root.get(LessonGroup_.GROUP_ID),groupId),
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

    private void modifyLesson(NativeLesson nativeLesson, Lesson lesson){
//        Courses courses = lessonNameRepository.getLessonNameByName(nativeLesson.getLesson())
//                .orElse(null);
//        courses = courses == null ? lessonNameRepository.save(new Courses(nativeLesson.getLesson().trim())) : courses;
//        lesson.setLessonsNameId(courses.getId());
//        lesson.setNumber(nativeLesson.getNumber());
//        Teacher teacher = teacherRepository.getTeacherByTeacherName(nativeLesson.getTeacher()
//                .replace("-- продолжение --", "")
//                .trim()).orElse(null);
//        teacher = teacher == null ? teacherRepository.save(new Teacher(nativeLesson.getTeacher()
//                .replace("-- продолжение --", "").trim())) : teacher;
//        lesson.setTeacherId(teacher.getId());
//        ClassRoom classRoom = classRoomRepository.getClassRoomByClassRoom(nativeLesson.getClassroom())
//                .orElse(null);
//        classRoom = classRoom == null ? classRoomRepository.save(new ClassRoom(nativeLesson.getClassroom().trim())) : classRoom;
//        lesson.setClassRoomId(classRoom.getId());
//        TypeOfLesson typeOfLesson = typeRepository.getTypeByType(nativeLesson.getType())
//                .orElse(null);
//        typeOfLesson = typeOfLesson == null ? typeRepository.save(new TypeOfLesson(nativeLesson.getType().trim())) : typeOfLesson;
//        lesson.setTypeId(typeOfLesson.getId());
    }

    private void modifyLesson(LessonDto lessonDto, Lesson lesson){
//        if(lessonDto.getLesson() != null) {
//            Courses courses = lessonNameRepository.getLessonNameByName(lessonDto.getLesson())
//                    .orElse(null);
//            courses = courses == null ? lessonNameRepository.save(new Courses(lessonDto.getLesson().trim())) : courses;
//            lesson.setLessonsNameId(courses.getId());
//        }
//        if(lessonDto.getTeacherName() != null) {
//            Teacher teacher = teacherRepository.getTeacherByTeacherName(lessonDto.getTeacherName()
//                    .replace("-- продолжение --", "")
//                    .trim()).orElse(null);
//            teacher = teacher == null ? teacherRepository.save(new Teacher(lessonDto.getTeacherName()
//                    .replace("-- продолжение --", "").trim())) : teacher;
//            lesson.setTeacherId(teacher.getId());
//        }
//        if(lessonDto.getClassRoom() != null) {
//            ClassRoom classRoom = classRoomRepository.getClassRoomByClassRoom(lessonDto.getClassRoom())
//                    .orElse(null);
//            classRoom = classRoom == null ? classRoomRepository.save(new ClassRoom(lessonDto.getClassRoom().trim())) : classRoom;
//            lesson.setClassRoomId(classRoom.getId());
//        }
//        if(lessonDto.getType() != null) {
//            TypeOfLesson typeOfLesson = typeRepository.getTypeByType(lessonDto.getType())
//                    .orElse(null);
//            typeOfLesson = typeOfLesson == null ? typeRepository.save(new TypeOfLesson(lessonDto.getType().trim())) : typeOfLesson;
//            lesson.setTypeId(typeOfLesson.getId());
//        }
    }


    private void getTimeByNumberOfLesson(Long numbersOfLesson, Lesson lesson,Week week, String day){
        switch (day){
            case "ПОНЕДЕЛЬНИК" -> lesson.setDay(week.getFromWeek());
            case "ВТОРНИК" -> lesson.setDay(week.getFromWeek().plusDays(1));
            case "СРЕДА" -> lesson.setDay(week.getFromWeek().plusDays(2));
            case "ЧЕТВЕРГ" -> lesson.setDay(week.getFromWeek().plusDays(3));
            case "ПЯТНИЦА" -> lesson.setDay(week.getFromWeek().plusDays(4));
            case "СУББОТА" -> lesson.setDay(week.getFromWeek().plusDays(5));
        }
        switch (numbersOfLesson.intValue()){
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
