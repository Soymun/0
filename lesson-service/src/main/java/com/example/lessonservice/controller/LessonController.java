package com.example.lessonservice.controller;


import com.example.lessonservice.dto.Lesson.*;
import com.example.lessonservice.facade.LessonFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/v1")
public class LessonController {

    private final LessonFacade lessonSaveFacade;

    public LessonController(LessonFacade lessonSaveFacade) {
        this.lessonSaveFacade = lessonSaveFacade;
    }

    @PostMapping("/lesson/file")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveFromFile(@RequestParam MultipartFile multipartFile,
                                          @RequestParam Long id,
                                          @RequestParam Long countGroup) throws IOException {
        if(!Objects.requireNonNull(multipartFile.getResource().getFilename()).split("\\.")[1].equals("xlsx")
                && !Objects.requireNonNull(multipartFile.getResource().getFilename()).split("\\.")[1].equals("xls")){
            throw new RuntimeException("Не тот тип файла");
        }
        return lessonSaveFacade.saveFromFile(multipartFile, id, countGroup);
    }

    @PostMapping("/lessons")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> addWeekLesson(@RequestBody LessonCreateDto lessonCreateDto){
        if(lessonCreateDto == null){
            throw new RuntimeException("Невозможно сохранить расписание");
        }
        return lessonSaveFacade.saveLesson(lessonCreateDto);
    }

    @GetMapping("/lessons")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getLesson(@RequestBody GetLessonDto getLessonDto){
        if(getLessonDto == null){
            throw new RuntimeException("Невозможно найти расписание");
        }
        return lessonSaveFacade.getLesson(getLessonDto);
    }

    @GetMapping("/teacher/lessons")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> getLessonForTeacher(@RequestBody GetLessonTeacher getLessonDto){
        if(getLessonDto == null){
            throw new RuntimeException("Невозможно найти расписание");
        }
        return lessonSaveFacade.getLessonForTeacher(getLessonDto);
    }

    @GetMapping("/lessons/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getUpdateLesson(@RequestBody UpdateLessonDto updateLessonDto){
        if(updateLessonDto == null || updateLessonDto.getLocalDateTime() == null){
            throw new RuntimeException("Невозможно найти пары");
        }
        return lessonSaveFacade.getLessonForUpdate(updateLessonDto);
    }

    @PatchMapping ("/lessons")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> patchLesson(@RequestBody LessonToUpdateDto lesson){
        if(lesson.getIds() == null){
            throw new RuntimeException("Невозможно обновить пары");
        }
        return lessonSaveFacade.patchLesson(lesson);
    }

    @DeleteMapping("/lessons")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteLessons(@RequestBody DeleteLessons deleteLessons){
        if(deleteLessons.getLessonIds() == null){
            throw new RuntimeException("Невозможно удалить пары");
        }
        return lessonSaveFacade.deleteLessons(deleteLessons.getLessonIds());
    }

    @DeleteMapping("/lesson/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id){
        if(id == 0){
            throw new RuntimeException("Невозможно удалить пару");
        }
        return lessonSaveFacade.deleteLesson(id);
    }

    @GetMapping("/teacher")
    public ResponseEntity<?> whereIsMyTeacher(@RequestParam Long id){
        return lessonSaveFacade.whereIsMyTeacher(id);
    }
}
