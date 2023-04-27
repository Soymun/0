package com.example.lessonservice.controller;

import com.example.lessonservice.dto.TypeLesson.TypeLessonDto;
import com.example.lessonservice.service.Impl.TypeLessonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TypeOfLessonController {

    private final TypeLessonServiceImpl typeLessonService;

    @GetMapping("/type")
    public ResponseEntity<?> getType(){
        return ResponseEntity.ok(typeLessonService.getTypeLessons());
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<?> getTypeById(@PathVariable Long id){
        return ResponseEntity.ok(typeLessonService.getTypeLessonById(id));
    }


    @PostMapping("/type")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveType(@RequestBody TypeLessonDto typeLessonDto){
        typeLessonService.saveTypeLesson(typeLessonDto);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/type")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateType(@RequestBody TypeLessonDto typeLessonDto){
        typeLessonService.updateTypeLesson(typeLessonDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/type/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteType(@PathVariable Long id){
        typeLessonService.deleteTypeLessonById(id);
        return ResponseEntity.noContent().build();
    }
}
