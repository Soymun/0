package com.example.courseservice.controller;

import com.example.courseservice.dto.marks.MarkCreateDto;
import com.example.courseservice.dto.marks.MarksUpdateDto;
import com.example.courseservice.service.impl.MarksServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MarksController {

    private final MarksServiceImpl marksService;

    @PostMapping("/mark")
    public ResponseEntity<?> saveCourse(@RequestBody MarkCreateDto markCreateDto){
        marksService.saveMark(markCreateDto);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/mark")
    public ResponseEntity<?> updateCourse(@RequestBody MarksUpdateDto marksUpdateDto){
        return ResponseEntity.ok(marksService.updateMarks(marksUpdateDto));
    }

    @DeleteMapping("/mark/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        marksService.deleteMarks(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mark/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id){
        return ResponseEntity.ok(marksService.getMarksById(id));
    }

    @GetMapping("/user/mark/{id}")
    public ResponseEntity<?> getCourseByUserId(@PathVariable Long id){
        return ResponseEntity.ok(marksService.getMarksByUserId(id));
    }

    @GetMapping("/mark/{courseId}")
    public ResponseEntity<?> getCourseByUserId(@PathVariable Long courseId, @RequestParam List<Long> id){
        return ResponseEntity.ok(marksService.getMarksByCoursesAndGroup(courseId, id));
    }
}
