package com.example.courseservice.controller;


import com.example.courseservice.dto.course.CourseCreateDto;
import com.example.courseservice.dto.course.CourseUpdateDto;
import com.example.courseservice.service.impl.CourseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CourseController {

    private final CourseServiceImpl courseService;

    @PostMapping("/course")
    public ResponseEntity<?> saveCourse(@RequestBody CourseCreateDto courseCreateDto){
        courseService.saveCourse(courseCreateDto);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/course")
    public ResponseEntity<?> updateCourse(@RequestBody CourseUpdateDto courseUpdateDto){
        return ResponseEntity.ok(courseService.updateCourse(courseUpdateDto));
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/university/course/{id}")
    public ResponseEntity<?> getcourseByUniversityId(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourseByUniversityId(id));
    }

    @GetMapping("/course")
    public ResponseEntity<?> getCourseByNameAndNumberCourse(@RequestParam String name, @RequestParam Long number){
        return ResponseEntity.ok(courseService.getCourseByNameAndNumberCourse(name, number));
    }
}
