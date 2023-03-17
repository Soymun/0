package com.example.demo.Controller;


import com.example.demo.DTO.Teacher.TeacherDto;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ytsu")
public class TeacherController {

    private final TeacherServiceImpl teacherService;

    @Autowired
    public TeacherController(TeacherServiceImpl teacherService) {
        this.teacherService = teacherService;
    }

    @PatchMapping("/teacher")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> updateTeacher(@RequestBody TeacherDto teacherDto){
        return ResponseEntity.ok(ResponseDto.builder().body(teacherService.updateTeacher(teacherDto)).build());
    }

    @DeleteMapping("/teacher/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBid(@PathVariable Long id){
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getTeachers(){
        return ResponseEntity.ok(ResponseDto.builder().body(teacherService.getListTeacher()).build());
    }

    @GetMapping("/teacher/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(teacherService.getTeacher(id)).build());
    }
}
