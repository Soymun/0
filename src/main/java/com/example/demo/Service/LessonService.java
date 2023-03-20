package com.example.demo.Service;

import com.example.demo.DTO.LessonDto;
import com.example.demo.DTO.TeacherLessonDto;
import com.example.demo.Entity.Group;
import com.example.demo.Entity.LessonGroup;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface LessonService {

    LessonDto saveLesson(LessonDto lessonDto,Long weekId, String day);

    LessonDto updateLesson(LessonDto lessonDto);

    List<LessonDto> getLessonForTeacher(Long id, LocalDateTime day, LocalDateTime day2);

    List<LessonDto> getLesson(Long groupId, LocalDate day, LocalDate day2);

    Map<LessonGroup, String>  saveLessonFromFile(MultipartFile file) throws IOException, InterruptedException, ExecutionException;

    List<LessonDto> getUpdateLesson(Long groupId, String nameLesson, String type);

    void deleteLesson(Long id);

    void deleteLessons(List<Long> ids);

    void saveLessonGroup(LessonGroup lessonGroup);

    LessonDto getLessonById(Long id);
}
