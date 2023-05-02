package com.example.universityservice.controller;


import com.example.universityservice.dto.classroom.ClassRoomCreateDto;
import com.example.universityservice.dto.classroom.ClassRoomUpdateDto;
import com.example.universityservice.service.impl.ClassRoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ClassRoomController {

    private final ClassRoomServiceImpl classRoomService;

    @GetMapping("/classRoom/{id}")
    public ResponseEntity<?> getClassRoom(@PathVariable Long id){
        return ResponseEntity.ok(classRoomService.getClassRoomById(id));
    }

    @GetMapping("/classRoom/{id}/{name}")
    public ResponseEntity<?> getClassRoomByNameAndUniversityId(@PathVariable Long id, @PathVariable String name){
        return ResponseEntity.ok(classRoomService.getClassRoomByNameAndUniversityId(name, id));
    }

    @PostMapping("/classRoom")
    public ResponseEntity<?> saveClassRoom(@RequestBody ClassRoomCreateDto classRoomCreateDto){
        classRoomService.saveClassRoom(classRoomCreateDto);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/classRoom/{id}")
    public ResponseEntity<?> deleteClassRoom(@PathVariable Long id){
        classRoomService.deleteClassRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/classRoom")
    public ResponseEntity<?> updateClassRoom(@RequestBody ClassRoomUpdateDto classRoomUpdateDto){
        return ResponseEntity.ok(classRoomService.updateClassRoom(classRoomUpdateDto));
    }

    @GetMapping("/classRoom/university/{id}")
    public ResponseEntity<?> getClassRoomByUniversityId(@PathVariable Long id){
        return ResponseEntity.ok(classRoomService.getClassRoomByUniversityId(id));
    }
}
