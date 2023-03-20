package com.example.demo.Facade;


import com.example.demo.DTO.Lesson.*;
import com.example.demo.Entity.Group;
import com.example.demo.Entity.LessonGroup;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.GroupServiceImpl;
import com.example.demo.Service.Impl.LessonServiceImpl;
import com.example.demo.Service.Impl.WeekServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LessonFacade {
    private final GroupServiceImpl groupService;

    private final LessonServiceImpl lessonService;
    private final WeekServiceImpl weekService;

    public ResponseEntity<?> saveFromFile(MultipartFile multipartFile) throws IOException {
        Map<LessonGroup, String> map = lessonService.saveLessonFromFile(multipartFile);
        map.entrySet().stream().parallel().forEach(n -> {
            Group group = groupService.getGroupByName(n.getValue().trim().toUpperCase());
            LessonGroup lessonGroup = n.getKey();
            lessonGroup.setGroupId(group.getId());
            lessonService.saveLessonGroup(lessonGroup);
        });
        return ResponseEntity.ok("Suggest");
    }

    public ResponseEntity<?> getLesson(GetLessonDto getLessonDto) {
        List<LessonDto> lessonDtos = lessonService.getLesson(getLessonDto.getGroupId(), getLessonDto.getDay(), getLessonDto.getDay2());
        return ResponseEntity.ok(convert(lessonDtos));
    }

    public ResponseEntity<?> getLessonForTeacher(GetLessonTeacher getLessonDto) {
        List<LessonDto> lessonDtos = lessonService.getLessonForTeacher(getLessonDto.getId(), getLessonDto.getDay(), getLessonDto.getDay2());
        return ResponseEntity.ok(convert(lessonDtos));
    }

    public ResponseEntity<?> getLessonForUpdate(UpdateLessonDto updateLessonDto) {
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
            getUpdateLessonDto.getWeeks().add(weekService.findWeekByDay(n.getFromTime()));
        });
        return ResponseEntity.ok(ResponseDto.builder().body(getUpdateLessonDto).build());
    }

    public ResponseEntity<?> patchLesson(LessonToUpdateDto lesson) {
        lesson.getIds().stream().parallel().forEach(
                n -> {
                    LessonDto lessonDto = lessonService.getLessonById(n);
                    if (lesson.getLesson() != null) {
                        lessonDto.setLesson(lesson.getLesson());
                    }
                    if (lesson.getTeacher() != null) {
                        lessonDto.setTeacherName(lesson.getTeacher());
                    }
                    if (lesson.getClassRoom() != null) {
                        lessonDto.setClassRoom(lesson.getClassRoom());
                    }
                    if (lesson.getDayOfWeek() != null && !lesson.getDayOfWeek().getDayOfWeek().equals(lessonDto.getDay().getDayOfWeek())) {
                        int range = lesson.getDayOfWeek().getDayOfWeek().compareTo(lessonDto.getDay().getDayOfWeek());
                        lessonDto.setDay(lessonDto.getDay().plusDays(range));
                    }
                    if (lesson.getFromTime() != null) {
                        lessonDto.setFromTime(lessonDto.getFromTime().withHour(lesson.getFromTime().getHour()).withMinute(lesson.getFromTime().getMinute()));
                    }
                    if (lesson.getToTime() != null) {
                        lessonDto.setToTime(lessonDto.getToTime().withHour(lesson.getToTime().getHour()).withMinute(lesson.getToTime().getMinute()));

                    }
                    if (lesson.getType() != null) {
                        lessonDto.setType(lesson.getType());
                    }
                    lessonService.updateLesson(lessonDto);
                }
        );
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    public ResponseEntity<?> updateLesson(LessonDto lessonDto) {
        return ResponseEntity.ok(ResponseDto.builder().body(lessonService.updateLesson(lessonDto)).build());
    }

    public ResponseEntity<?> addLesson(AddLessonByWeek addLessonByWeek) {
        addLessonByWeek.getWeeks().stream().parallel().forEach(n -> {
            LessonDto lessonDto = new LessonDto();
            lessonDto.setLesson(addLessonByWeek.getLesson());
            lessonDto.setNumber(addLessonByWeek.getNumber());
            lessonDto.setType(addLessonByWeek.getType());
            lessonDto.setClassRoom(addLessonByWeek.getClassRoom());
            lessonDto.setTeacherName(addLessonByWeek.getTeacher());
            LessonDto savedLesson = lessonService.saveLesson(lessonDto, n, addLessonByWeek.getDay());
            LessonGroup lessonGroup = new LessonGroup();
            lessonGroup.setLessonId(savedLesson.getId());
            lessonGroup.setGroupId(addLessonByWeek.getGroupId());
            lessonService.saveLessonGroup(lessonGroup);
        });
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    public ResponseEntity<?> deleteLesson(Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> deleteLessons(List<Long> list) {
        lessonService.deleteLessons(list);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> whereIsMyTeacher(Long id) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime day2 = localDateTime.withHour(23).withMinute(59);
        List<LessonDto> lessonDtos = lessonService.getLessonForTeacher(id, localDateTime, day2);
        if (lessonDtos.size() == 0) {
            return ResponseEntity.ok(ResponseDto.builder().body("Учитель уехал на бали").build());
        } else {
            Map<LocalDateTime, String> map = new HashMap<>();
            lessonDtos.forEach(n -> {
                if (!map.containsKey(n.getFromTime())) {
                    map.put(n.getFromTime(), n.getClassRoom());
                }
            });
            return ResponseEntity.ok(ResponseDto.builder().body(map).build());
        }
    }

    private Map<LocalDate, ListOutputLessonDto> convert(List<LessonDto> lessonDtos){
        Map<LocalDate, ListOutputLessonDto> map = new LinkedHashMap<>();
        lessonDtos.forEach(n -> {
                    ListOutputLessonDto lessonDto = map.getOrDefault(n.getDay(), new ListOutputLessonDto());
                    OutputLessonDto outputLessonDto = new OutputLessonDto(n);
                    Map<OutputLessonDto,OutputLessonDto> mapOutput = lessonDto.getOutputLessonDtos();
                    OutputLessonDto outputLessonDtoFromMap = mapOutput.getOrDefault(outputLessonDto, null);
                    if(outputLessonDtoFromMap == null){
                        mapOutput.put(outputLessonDto, outputLessonDto);
                    }
                    else {
                        if(outputLessonDtoFromMap.getNumber().stream().anyMatch(o -> Math.abs(o.intValue() - outputLessonDto.getNumber().get(0).intValue()) == 1)){
                            outputLessonDtoFromMap.setFromTime(outputLessonDto.getFromTime().isBefore(outputLessonDtoFromMap.getFromTime())? outputLessonDto.getFromTime():outputLessonDtoFromMap.getFromTime());
                            outputLessonDtoFromMap.setToTime(outputLessonDto.getToTime().isAfter(outputLessonDtoFromMap.getToTime())? outputLessonDto.getToTime():outputLessonDtoFromMap.getToTime());
                            mapOutput.put(outputLessonDtoFromMap, outputLessonDtoFromMap);
                        }
                        else {
                            mapOutput.put(outputLessonDto, outputLessonDto);
                        }
                    }
                    lessonDto.setOutputLessonDtos(mapOutput);
                    map.put(n.getDay(), lessonDto);
                }
        );
        return map;
    }
}
