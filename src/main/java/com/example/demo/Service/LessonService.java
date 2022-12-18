package com.example.demo.Service;

import com.example.demo.DTO.LessonDto;
import com.example.demo.Entity.Group;
import com.example.demo.Entity.LessonGroup;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface LessonService {

    LessonDto saveLesson(LessonDto lessonDto);

    LessonDto updateLesson(LessonDto lessonDto);

    List<LessonDto> getLesson(Long groupId, LocalDateTime day, LocalDateTime day2);

    Map<LessonGroup, String>  saveLessonFromFile(MultipartFile file) throws IOException;

    void deleteLesson(Long id);

    void deleteLesson(List<Long> ids);
}
