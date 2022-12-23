package com.example.demo.Controller;


import com.example.demo.DTO.GetLessonDto;
import com.example.demo.DTO.GetLessonTeacher;
import com.example.demo.DTO.LessonDto;
import com.example.demo.Entity.Lesson;
import com.example.demo.Facade.LessonSaveFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/ytsu")
public class LessonController {

    private final LessonSaveFacade lessonSaveFacade;

    public LessonController(LessonSaveFacade lessonSaveFacade) {
        this.lessonSaveFacade = lessonSaveFacade;
    }

    @PostMapping("/lesson/file")
    public ResponseEntity<?> saveFromFile(@RequestParam MultipartFile multipartFile) throws IOException {
        if(!Objects.requireNonNull(multipartFile.getResource().getFilename()).split("\\.")[1].equals("xlsx")
                || !Objects.requireNonNull(multipartFile.getResource().getFilename()).split("\\.")[1].equals("xls")){
            throw new RuntimeException("Не тот тип файла");
        }
        return lessonSaveFacade.saveFromFile(multipartFile);
    }

    @GetMapping("/lessons")
    public ResponseEntity<?> getLesson(@RequestBody GetLessonDto getLessonDto){
        if(getLessonDto == null){
            throw new RuntimeException("Невозможно найти расписание");
        }
        return lessonSaveFacade.getLesson(getLessonDto);
    }

    @GetMapping("/teacher/lessons")
    public ResponseEntity<?> getLesson(@RequestBody GetLessonTeacher getLessonDto){
        if(getLessonDto == null){
            throw new RuntimeException("Невозможно найти расписание");
        }
        return lessonSaveFacade.getLessonForTeacher(getLessonDto);
    }

    @PostMapping("/lesson")
    public ResponseEntity<?> saveLesson(@RequestBody LessonDto lessonDto){
        if(lessonDto == null){
            throw new RuntimeException("Невозможно сохранить расписание");
        }
        return lessonSaveFacade.saveLesson(lessonDto);
    }
}
