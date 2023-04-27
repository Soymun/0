package com.example.lessonservice.controller;


import com.example.lessonservice.dto.Week.WeekCreateDto;
import com.example.lessonservice.dto.Week.WeekDto;
import com.example.lessonservice.dto.Week.WeekGetDto;
import com.example.lessonservice.service.Impl.WeekServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class WeekController {

    private final WeekServiceImpl weekService;

    @PostMapping("/week")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveWeek(@RequestBody WeekCreateDto weekCreateDto){
        weekService.saveWeek(weekCreateDto);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/week")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateWeek(@RequestBody WeekDto weekDto){
        return ResponseEntity.ok(weekService.updateWeek(weekDto));
    }

    @DeleteMapping("/week/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteWeek(@PathVariable Long id){
        weekService.deleteWeek(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/week/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getWeekById(@PathVariable Long id){
        return ResponseEntity.ok(weekService.findWeekById(id));
    }

    @GetMapping("/week")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getWeekByDay(@RequestBody WeekGetDto weekGetDto){
        return ResponseEntity.ok(weekService.findWeekByDay(weekGetDto.getDay()));
    }
}
