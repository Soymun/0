package com.example.demo.Controller;


import com.example.demo.DTO.*;
import com.example.demo.Facade.LessonFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/ytsu")
public class LessonController {

    private final LessonFacade lessonSaveFacade;

    public LessonController(LessonFacade lessonSaveFacade) {
        this.lessonSaveFacade = lessonSaveFacade;
    }

    @PostMapping("/lesson/file")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveFromFile(@RequestParam MultipartFile multipartFile) throws IOException {
        if(!Objects.requireNonNull(multipartFile.getResource().getFilename()).split("\\.")[1].equals("xlsx")
                && !Objects.requireNonNull(multipartFile.getResource().getFilename()).split("\\.")[1].equals("xls")){
            throw new RuntimeException("Не тот тип файла");
        }
        return lessonSaveFacade.saveFromFile(multipartFile);
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

    @PostMapping("/lesson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveLesson(@RequestBody LessonDto lessonDto){
        if(lessonDto == null){
            throw new RuntimeException("Невозможно сохранить расписание");
        }
        return lessonSaveFacade.saveLesson(lessonDto);
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

    @PutMapping("/lesson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateLesson(@RequestBody LessonDto lessonDto){
        if(lessonDto.getId() == 0){
            throw new RuntimeException("Невозможно обновить пару");
        }
        return lessonSaveFacade.updateLesson(lessonDto);
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

    @PostMapping("/lessons")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> addWeekLesson(@RequestBody AddLessonByWeek addLessonByWeek){
        if(addLessonByWeek == null){
            throw new RuntimeException("Невозможно сохранить расписание");
        }
        return lessonSaveFacade.addLesson(addLessonByWeek);
    }

    @GetMapping("/teacher")
    public ResponseEntity<?> whereIsMyTeacher(@RequestParam String name){
        if(name.equals("")){
            throw new RuntimeException("Невозможно найти учителя");
        }
        return lessonSaveFacade.whereIsMyTeacher(name);
    }
}
