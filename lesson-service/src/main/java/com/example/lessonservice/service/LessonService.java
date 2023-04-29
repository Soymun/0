package com.example.lessonservice.service;


import com.example.lessonservice.dto.Lesson.LessonCreateDto;
import com.example.lessonservice.dto.Lesson.LessonDto;
import com.example.lessonservice.dto.Lesson.LessonToUpdateDto;
import com.example.lessonservice.entity.LessonGroup;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface LessonService {

    Long saveLesson(LessonCreateDto lessonDto, Long weekId, String day);

    void updateLesson(LessonToUpdateDto lessonDto);

    List<LessonDto> getLessonForTeacher(Long id, LocalDateTime day, LocalDateTime day2);

    List<LessonDto> getLesson(Long groupId, LocalDate day, LocalDate day2);

    Map<LessonGroup, String>  saveLessonFromFile(MultipartFile file, Long universityId, Long countGroup) throws IOException, InterruptedException, ExecutionException;

    List<LessonDto> getUpdateLesson(Long groupId, Long coursesId, Long typeId);

    void deleteLesson(Long id);

    void deleteLessons(List<Long> ids);

    void saveLessonGroup(LessonGroup lessonGroup);
}
