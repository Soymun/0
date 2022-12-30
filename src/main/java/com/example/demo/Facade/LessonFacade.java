package com.example.demo.Facade;


import com.example.demo.DTO.*;
import com.example.demo.Entity.LessonGroup;
import com.example.demo.Entity.Week;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.GroupServiceImpl;
import com.example.demo.Service.Impl.LessonServiceImpl;
import com.example.demo.Service.Impl.WeekServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class LessonFacade {

    private final LessonServiceImpl lessonService;

    private final GroupServiceImpl groupService;

    private final WeekServiceImpl weekService;

    public LessonFacade(LessonServiceImpl lessonService, GroupServiceImpl groupService, WeekServiceImpl weekService) {
        this.lessonService = lessonService;
        this.groupService = groupService;
        this.weekService = weekService;
    }

    public ResponseEntity<?> saveFromFile(MultipartFile multipartFile) throws IOException {
        Map<LessonGroup, String> map = lessonService.saveLessonFromFile(multipartFile);
        map.entrySet().stream().parallel().forEach(n -> {
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
                        && outputLessonDto.getType().equals(outputLessonDto1.getType())
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
                        && outputLessonDto.getType().equals(outputLessonDto1.getType())
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

    public ResponseEntity<?> getLessonForUpdate(UpdateLessonDto updateLessonDto){
        List<LessonDto> lessonDtos = lessonService
                .getUpdateLesson(updateLessonDto.getGroupId(), updateLessonDto.getNameLesson(), updateLessonDto.getType())
                .stream()
                .filter(n -> n.getDay().getDayOfWeek().equals(updateLessonDto.getLocalDateTime().getDayOfWeek()))
                .toList();
        GetUpdateLessonDto getUpdateLessonDto = new GetUpdateLessonDto();
        lessonDtos.forEach(n -> {
            getUpdateLessonDto.setLesson(n.getLesson());
            getUpdateLessonDto.setType(n.getType());
            getUpdateLessonDto.setTeacherName(n.getTeacherName());
            getUpdateLessonDto.setClassRoom(n.getClassRoom());
            getUpdateLessonDto.setFromTime(n.getFromTime());
            getUpdateLessonDto.setToTime(n.getToTime());
            getUpdateLessonDto.setNumber(n.getNumber());
            getUpdateLessonDto.getIds().add(n.getId());
            getUpdateLessonDto.getWeeks().add(weekService.findWeekId(n.getFromTime()));
        });
        return ResponseEntity.ok(ResponseDto.builder().body(getUpdateLessonDto).build());
    }

    public ResponseEntity<?> patchLesson(LessonToUpdateDto lesson){
        lesson.getIds().forEach(
                n -> {
                    LessonDto lessonDto = lessonService.getLessonById(n);
                    if(lesson.getLesson() != null){
                        lessonDto.setLesson(lesson.getLesson());
                    }
                    if(lesson.getTeacher() != null){
                        lessonDto.setTeacherName(lesson.getTeacher());
                    }
                    if(lesson.getClassRoom() != null){
                        lessonDto.setClassRoom(lesson.getClassRoom());
                    }
                    if(lesson.getDayOfWeek() != null && !lesson.getDayOfWeek().getDayOfWeek().equals(lessonDto.getDay().getDayOfWeek())){
                        int range = lesson.getDayOfWeek().getDayOfWeek().compareTo(lessonDto.getDay().getDayOfWeek());
                        lessonDto.setDay(lessonDto.getDay().plusDays(range));
                    }
                    if(lesson.getFromTime() != null){
                        lessonDto.setFromTime(lessonDto.getFromTime().withHour(lesson.getFromTime().getHour()).withMinute(lesson.getFromTime().getMinute()));
                    }
                    if(lesson.getToTime() != null){
                        lessonDto.setToTime(lessonDto.getToTime().withHour(lesson.getToTime().getHour()).withMinute(lesson.getToTime().getMinute()));

                    }
                    if(lesson.getType() != null){
                        lessonDto.setType(lesson.getType());
                    }
                    lessonService.updateLesson(lessonDto);
                }

        );
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    public ResponseEntity<?> updateLesson(LessonDto lessonDto){
        return ResponseEntity.ok(ResponseDto.builder().body(lessonService.updateLesson(lessonDto)).build());
        }

    public ResponseEntity<?> addLesson(AddLessonByWeek addLessonByWeek){
        addLessonByWeek.getWeeks().forEach(n -> {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setLesson(addLessonByWeek.getLesson());
            lessonDto.setNumber(addLessonByWeek.getNumber());
            lessonDto.setType(addLessonByWeek.getType());
            lessonDto.setClassRoom(addLessonByWeek.getClassRoom());
            lessonDto.setTeacherName(addLessonByWeek.getTeacher());
            Week week = weekService.findWeekById(n);
            switch (addLessonByWeek.getDay()){
                    case "ПОНЕДЕЛЬНИК" -> lessonDto.setDay(week.getFromWeek());
                    case "ВТОРНИК" -> lessonDto.setDay(week.getFromWeek().plusDays(1));
                    case "СРЕДА" -> lessonDto.setDay(week.getFromWeek().plusDays(2));
                    case "ЧЕТВЕРГ" -> lessonDto.setDay(week.getFromWeek().plusDays(3));
                    case "ПЯТНИЦА" -> lessonDto.setDay(week.getFromWeek().plusDays(4));
                    case "СУББОТА" -> lessonDto.setDay(week.getFromWeek().plusDays(5));
            }
            switch (lessonDto.getNumber().intValue()){
                case 1 -> {
                    lessonDto.setFromTime(lessonDto.getDay().plusHours(8).plusMinutes(30));
                    lessonDto.setToTime(lessonDto.getDay().plusHours(10).plusMinutes(0));
                }
                case 2 -> {
                    lessonDto.setFromTime(lessonDto.getDay().plusHours(10).plusMinutes(10));
                    lessonDto.setToTime(lessonDto.getDay().plusHours(11).plusMinutes(40));
                }
                case 3 -> {
                    lessonDto.setFromTime(lessonDto.getDay().plusHours(11).plusMinutes(50));
                    lessonDto.setToTime(lessonDto.getDay().plusHours(13).plusMinutes(20));
                }
                case 4 -> {
                    lessonDto.setFromTime(lessonDto.getDay().plusHours(12).plusMinutes(20));
                    lessonDto.setToTime(lessonDto.getDay().plusHours(13).plusMinutes(50));
                }
                case 5 -> {
                    lessonDto.setFromTime(lessonDto.getDay().plusHours(14).plusMinutes(0));
                    lessonDto.setToTime(lessonDto.getDay().plusHours(15).plusMinutes(30));
                }
                case 6 -> {
                    lessonDto.setFromTime(lessonDto.getDay().plusHours(15).plusMinutes(40));
                    lessonDto.setToTime(lessonDto.getDay().plusHours(17).plusMinutes(10));
                }
                case 7 -> {
                    lessonDto.setFromTime(lessonDto.getDay().plusHours(17).plusMinutes(30));
                    lessonDto.setToTime(lessonDto.getDay().plusHours(19).plusMinutes(0));
                }
            }
            LessonDto savedLesson = lessonService.saveLesson(lessonDto);
            LessonGroup lessonGroup = new LessonGroup();
            lessonGroup.setLessonId(savedLesson.getId());
            lessonGroup.setGroupId(addLessonByWeek.getGroupId());
            lessonService.saveLessonGroup(lessonGroup);
        });
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    public ResponseEntity<?> deleteLesson(Long id){
        lessonService.deleteLesson(id);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    public ResponseEntity<?> deleteLessons(List<Long> list){
        lessonService.deleteLesson(list);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    public ResponseEntity<?> whereIsMyTeacher(String name){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime day2 = localDateTime.withHour(23).withMinute(59);
        List<TeacherLessonDto> lessonDtos = lessonService.getLessonForTeacher(name, localDateTime, day2);
        if(lessonDtos.size() == 0){
            return ResponseEntity.ok(ResponseDto.builder().body("Учитель уехал на бали").build());
        }
        else {
            Map<LocalDateTime, String> map = new HashMap<>();
            lessonDtos.forEach(n -> {
                if(!map.containsKey(n.getFromTime())){
                    map.put(n.getFromTime(), n.getClassRoom());
                }
            });
            return ResponseEntity.ok(ResponseDto.builder().body(map).build());
        }
    }
}
