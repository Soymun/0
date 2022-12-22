package com.example.demo.Controller;


import com.example.demo.DTO.GetLessonDto;
import com.example.demo.Facade.LessonSaveFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/timetable")
public class LessonController {

    private final LessonSaveFacade lessonSaveFacade;

    public LessonController(LessonSaveFacade lessonSaveFacade) {
        this.lessonSaveFacade = lessonSaveFacade;
    }

    @PostMapping("/file")
    public ResponseEntity<?> saveFromFile(@RequestParam MultipartFile multipartFile) throws IOException {
        return lessonSaveFacade.saveFromFile(multipartFile);
    }

    @GetMapping("/timetable")
    public ResponseEntity<?> getLesson(@RequestBody GetLessonDto getLessonDto){
        return lessonSaveFacade.getLesson(getLessonDto);
    }
}
