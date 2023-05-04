package com.example.profileservice.controller;

import com.example.profileservice.dto.student.StudentCreateDto;
import com.example.profileservice.dto.student.StudentUpdateDto;
import com.example.profileservice.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping("/student")
    public ResponseEntity<?> saveStudent(@RequestBody StudentCreateDto studentCreateDto){
        studentService.saveStudent(studentCreateDto);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/student")
    public ResponseEntity<?> updateStudent(@RequestBody StudentUpdateDto studentUpdateDto){
        return ResponseEntity.ok(studentService.updateStudent(studentUpdateDto));
    }

    @GetMapping("/student/group/{id}")
    public ResponseEntity<?> getStudentByGroupId(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentByGroupId(id));
    }
}
