package com.example.demo.Controller;


import com.example.demo.DTO.Marks.MarkCreateDto;
import com.example.demo.DTO.Marks.MarksUpdateDto;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.MarksServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ytsu")
public class MarksController {
    private final MarksServiceImpl marksService;

    public MarksController(MarksServiceImpl marksService) {
        this.marksService = marksService;
    }

    @GetMapping("/mark/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getMark(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(marksService.getMarksById(id)).build());
    }

    @GetMapping("/user/marks/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getMarks(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(marksService.getMarksByUserId(id)).build());
    }

    @GetMapping("/marks")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getMarksByGroupAndCourses(@RequestParam Long groupId,@RequestParam Long coursesId){
        return ResponseEntity.ok(ResponseDto.builder().body(marksService.getMarksByCoursesAndGroup(coursesId, groupId)).build());
    }

    @PostMapping("/mark")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> saveMark(@RequestBody MarkCreateDto marksDto){
        marksService.saveMark(marksDto);
        return ResponseEntity.ok(ResponseDto.builder().build());
    }

    @PutMapping("/mark")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> updateMark(@RequestBody MarksUpdateDto marksDto){
        return ResponseEntity.ok(ResponseDto.builder().body(marksService.updateMarks(marksDto)).build());
    }

    @DeleteMapping("/mark/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> deleteMark(@PathVariable Long id){
        marksService.deleteMarks(id);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }
}
