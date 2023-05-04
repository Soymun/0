package com.example.profileservice.controller;

import com.example.profileservice.dto.teacher.TeacherCreateDto;
import com.example.profileservice.dto.teacher.TeacherUpdateDto;
import com.example.profileservice.service.impl.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherServiceImpl teacherService;

    @PostMapping("/teacher")
    public ResponseEntity<?> saveTeacher(@RequestBody TeacherCreateDto teacherCreateDto){
        teacherService.saveTeacher(teacherCreateDto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<?> getTeacher(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getTeacher(id));
    }


    @GetMapping("/teacher/university/{id}")
    public ResponseEntity<?> getTeacherByUniversity(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getListTeacherByUniversityId(id));
    }

    @PatchMapping("/teacher")
    public ResponseEntity<?> updateTeacher(@RequestBody TeacherUpdateDto teacherUpdateDto){
        return ResponseEntity.ok(teacherService.updateTeacher(teacherUpdateDto));
    }

    @GetMapping("/teacher/{id}/{name}")
    public ResponseEntity<?> getTeacherByName(@PathVariable Long id, @PathVariable String name){
        return ResponseEntity.ok(teacherService.getTeacherByNameAndUniversityId(id, name));
    }

    @DeleteMapping("/teacher/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id){
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
