package com.example.demo.Facade;


import com.example.demo.DTO.GetLessonDto;
import com.example.demo.DTO.GroupDto;
import com.example.demo.DTO.LessonDto;
import com.example.demo.Entity.LessonGroup;
import com.example.demo.Service.Impl.GroupServiceImpl;
import com.example.demo.Service.Impl.LessonServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class LessonSaveFacade {

    private final LessonServiceImpl lessonService;

    private final GroupServiceImpl groupService;

    public LessonSaveFacade(LessonServiceImpl lessonService, GroupServiceImpl groupService) {
        this.lessonService = lessonService;
        this.groupService = groupService;
    }

    public ResponseEntity<?> saveFromFile(MultipartFile multipartFile) throws IOException {
        Map<LessonGroup, String> map = lessonService.saveLessonFromFile(multipartFile);
        map.entrySet().stream().forEach(n -> {
            GroupDto group = groupService.getGroupByName(n.getValue().trim().toUpperCase());
            LessonGroup lessonGroup = n.getKey();
            lessonGroup.setGroupId(group.getId());
            lessonService.saveLessonGroup(lessonGroup);
        });
        return ResponseEntity.ok("Suggest");
    }

    public ResponseEntity<?> saveLesson(LessonDto lessonDto){
        LessonDto lessonDto1 = lessonService.saveLesson(lessonDto);
        lessonDto.getGroup().forEach(n -> {
            LessonGroup lessonGroup = new LessonGroup();
            lessonGroup.setLessonId(lessonDto1.getId());
            lessonGroup.setGroupId(n);
            lessonService.saveLessonGroup(lessonGroup);
        });
        return ResponseEntity.ok("Suggest");
    }

    public ResponseEntity<?> getLesson(GetLessonDto getLessonDto){
        return ResponseEntity.ok(lessonService.getLesson(getLessonDto.getGroupId(), getLessonDto.getDay(), getLessonDto.getDay2()));
    }
}
