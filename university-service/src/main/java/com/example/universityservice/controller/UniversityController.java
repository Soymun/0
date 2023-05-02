package com.example.universityservice.controller;


import com.example.universityservice.dto.university.UniversityCreateDto;
import com.example.universityservice.dto.university.UniversityDto;
import com.example.universityservice.service.impl.UniversityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityServiceImpl universityService;

    @GetMapping("/university/{id}")
    public ResponseEntity<?> getUniversityById(@PathVariable Long id){
        return ResponseEntity.ok(universityService.getUniversityById(id));
    }

    @PostMapping("/university")
    public ResponseEntity<?> saveUniversity(@RequestBody UniversityCreateDto universityCreateDto){
        universityService.saveUniversity(universityCreateDto);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/university")
    public ResponseEntity<?> updateUniversity(@RequestBody UniversityDto universityDto){
        return ResponseEntity.ok(universityService.updateUniversity(universityDto));
    }

    @DeleteMapping("/university/{id}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Long id){
        universityService.deleteUniversityById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/universities")
    public ResponseEntity<?> getAllUniversity(){
        return ResponseEntity.ok(universityService.getAllUniversities());
    }
}
