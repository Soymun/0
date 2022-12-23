package com.example.demo.Facade;


import com.example.demo.DTO.*;
import com.example.demo.Entity.LessonGroup;
import com.example.demo.Service.Impl.GroupServiceImpl;
import com.example.demo.Service.Impl.LessonServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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
        List<LessonDto> lessonDtos = lessonService.getLesson(getLessonDto.getGroupId(), getLessonDto.getDay(), getLessonDto.getDay2());
        Map<LocalDateTime, ListOutputLessonDto> map = new LinkedHashMap<>();
        lessonDtos.forEach(n -> {
            if(map.containsKey(n.getDay())){
                ListOutputLessonDto lessonDto = map.get(n.getDay());
                lessonDto.getOutputLessonDtos().add(new OutputLessonDto(n));
                map.put(n.getDay(),lessonDto);
            }
            else {
                ListOutputLessonDto lessonDto = new ListOutputLessonDto();
                lessonDto.getOutputLessonDtos().add(new OutputLessonDto(n));
                map.put(n.getDay(), lessonDto);
            }
        }
        );
        Map<LocalDateTime, ListOutputLessonDto> map2 = new LinkedHashMap<>();
        for (Map.Entry<LocalDateTime, ListOutputLessonDto> entry: map.entrySet()){
            List<OutputLessonDto> list = entry.getValue().getOutputLessonDtos();
            for (int i = 0;i < list.size()-1; i++){
                OutputLessonDto outputLessonDto = list.get(i);
                OutputLessonDto outputLessonDto1 = list.get(i+1);
                if(outputLessonDto.getLesson().equals(outputLessonDto1.getLesson())
                        && outputLessonDto.getClassRoom().equals(outputLessonDto1.getClassRoom())
                        && outputLessonDto.getTeacherName().equals(outputLessonDto1.getTeacherName().replace("-- продолжение --", ""))
                        && outputLessonDto.getToTime().isBefore(outputLessonDto1.getFromTime())){
                    outputLessonDto.setToTime(outputLessonDto1.getToTime());
                    outputLessonDto.getNumber().addAll(outputLessonDto1.getNumber());
                    list.remove(i+1);
                    i--;
                }
            }
            List<OutputLessonDto> outputLessonDtos = list.stream().sorted(Comparator.comparingInt(o -> o.getNumber().get(0).intValue())).toList();
            map2.put(entry.getKey(), new ListOutputLessonDto(outputLessonDtos));
        }
        return ResponseEntity.ok(map2);
    }

    public ResponseEntity<?> getLessonForTeacher(GetLessonTeacher getLessonDto){
        List<TeacherLessonDto> lessonDtos = lessonService.getLessonForTeacher(getLessonDto.getTeacherName(), getLessonDto.getDay(), getLessonDto.getDay2());
        Map<LocalDateTime, ListTeacherLessonDto> map = new LinkedHashMap<>();
        lessonDtos.forEach(n -> {
                    if(map.containsKey(n.getDay())){
                        ListTeacherLessonDto lessonDto = map.get(n.getDay());
                        lessonDto.getTeacherLessonDtos().add(new OutputTeacherLessonDto(n));
                        map.put(n.getDay(),lessonDto);
                    }
                    else {
                        ListTeacherLessonDto lessonDto = new ListTeacherLessonDto();
                        lessonDto.getTeacherLessonDtos().add(new OutputTeacherLessonDto(n));
                        map.put(n.getDay(), lessonDto);
                    }
                }
        );
        Map<LocalDateTime, ListTeacherLessonDto> map2 = new LinkedHashMap<>();
        for (Map.Entry<LocalDateTime, ListTeacherLessonDto> entry: map.entrySet()){
            List<OutputTeacherLessonDto> list = entry.getValue().getTeacherLessonDtos();
            for (int i = 0;i < list.size()-1; i++){
                OutputTeacherLessonDto outputLessonDto = list.get(i);
                OutputTeacherLessonDto outputLessonDto1 = list.get(i+1);
                if(outputLessonDto.getLesson().equals(outputLessonDto1.getLesson())
                        && outputLessonDto.getClassRoom().equals(outputLessonDto1.getClassRoom())
                        && outputLessonDto.getTeacherName().equals(outputLessonDto1.getTeacherName().replace("-- продолжение --", ""))
                        && outputLessonDto.getToTime().isBefore(outputLessonDto1.getFromTime())
                        && outputLessonDto.getGroup().containsAll(outputLessonDto1.getGroup())){
                    outputLessonDto.setToTime(outputLessonDto1.getToTime());
                    outputLessonDto.getNumber().addAll(outputLessonDto1.getNumber());
                    outputLessonDto.getGroup().addAll(outputLessonDto1.getGroup());
                    list.remove(i+1);
                    i--;
                }
            }
            List<OutputTeacherLessonDto> outputLessonDtos = list.stream().sorted(Comparator.comparingInt(o -> o.getNumber().get(0).intValue())).toList();
            map2.put(entry.getKey(), new ListTeacherLessonDto(outputLessonDtos));
        }
        return ResponseEntity.ok(map2);
    }

//    public ResponseEntity<?> getLessonForUpdate(UpdateLessonDto updateLessonDto){
//        List<LessonDto> lessonDtos = lessonService.getUpdateLesson(updateLessonDto.getGroupId());+
//    }
}
