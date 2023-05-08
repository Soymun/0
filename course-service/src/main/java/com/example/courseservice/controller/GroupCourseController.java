package com.example.courseservice.controller;


import com.example.courseservice.dto.geoupcourse.GroupCourseCreateDto;
import com.example.courseservice.service.impl.GroupCourseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class GroupCourseController {

    private final GroupCourseServiceImpl groupCourseService;

    @PostMapping("/groupcourse")
    public ResponseEntity<?> saveGroupCourse(@RequestBody GroupCourseCreateDto groupCourseCreateDto){
        groupCourseService.saveGroupCourse(groupCourseCreateDto);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/groupcourse/{id}")
    public ResponseEntity<?> deleteGroupCourse(@PathVariable Long id){
        groupCourseService.deleteGroupCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/{groupId}/{courseName}")
    public ResponseEntity<?> getCourse(@PathVariable Long groupId, @PathVariable String courseName){
        return ResponseEntity.ok(groupCourseService.getCourseByGroupIdAndCourseName(groupId, courseName));
    }
}
