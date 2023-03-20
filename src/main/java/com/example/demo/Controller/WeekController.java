package com.example.demo.Controller;

import com.example.demo.DTO.Week.WeekCreateDto;
import com.example.demo.DTO.Week.WeekDto;
import com.example.demo.DTO.Week.WeekGetDto;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.WeekServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ytsu")
public class WeekController {

    private final WeekServiceImpl weekService;

    @PostMapping("/week")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveWeek(@RequestBody WeekCreateDto weekCreateDto){
        weekService.saveWeek(weekCreateDto);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/bid")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateBid(@RequestBody WeekDto weekDto){
        return ResponseEntity.ok(ResponseDto.builder().body(weekService.updateWeek(weekDto)).build());
    }

    @DeleteMapping("/week/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteWeek(@PathVariable Long id){
        weekService.deleteWeek(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/week/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getBiByUserId(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(weekService.findWeekById(id)).build());
    }

    @GetMapping("/week")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getBiById(@RequestBody WeekGetDto weekGetDto){
        return ResponseEntity.ok(ResponseDto.builder().body(weekService.findWeekByDay(weekGetDto.getDay())).build());
    }
}
