package com.example.lessonservice.facade;


import com.example.lessonservice.dto.Group;
import com.example.lessonservice.dto.Lesson.*;
import com.example.lessonservice.entity.LessonGroup;
import com.example.lessonservice.service.Impl.LessonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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

    private final LessonServiceImpl lessonService;

    private final ObjectProvider<RestTemplate> restTemplateObjectProvider;

    public ResponseEntity<?> saveFromFile(MultipartFile multipartFile) throws IOException {
        Map<LessonGroup, String> map = lessonService.saveLessonFromFile(multipartFile);
        map.entrySet().stream().parallel().forEach(n -> {
            LessonGroup lessonGroup = n.getKey();
            lessonGroup.setGroupId(getGroupIdByName(n.getValue().trim().toUpperCase()));
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

    //TODO:ПЕРЕДЕЛАТЬ
    public ResponseEntity<?> getLessonForUpdate(UpdateLessonDto updateLessonDto) {
//        List<LessonDto> lessonDtos = lessonService
//                .getUpdateLesson(updateLessonDto.getGroupId(), updateLessonDto.getNameLesson(), updateLessonDto.getType())
//                .stream()
//                .filter(n -> n.getDay().getDayOfWeek().equals(updateLessonDto.getLocalDateTime().getDayOfWeek()))
//                .toList();
        GetUpdateLessonDto getUpdateLessonDto = new GetUpdateLessonDto();
//        lessonDtos.forEach(n -> {
//            getUpdateLessonDto.setLesson(n.getLesson());
//            getUpdateLessonDto.setType(n.getType());
//            getUpdateLessonDto.setTeacherName(n.getTeacherName());
//            getUpdateLessonDto.setClassRoom(n.getClassRoom());
//            getUpdateLessonDto.setFromTime(n.getFromTime());
//            getUpdateLessonDto.setToTime(n.getToTime());
//            getUpdateLessonDto.setNumber(n.getNumber());
//            getUpdateLessonDto.getIds().add(n.getId());
//            getUpdateLessonDto.getWeeks().add(weekService.findWeekByDay(n.getFromTime()));
//        });
        return ResponseEntity.ok(getUpdateLessonDto);
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
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<?> updateLesson(LessonDto lessonDto) {
        return ResponseEntity.status(201).build();
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
        return ResponseEntity.status(201).build();
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
            return ResponseEntity.ok("Учитель уехал на бали");
        } else {
            Map<LocalDateTime, String> map = new HashMap<>();
            lessonDtos.forEach(n -> {
                if (!map.containsKey(n.getFromTime())) {
                    map.put(n.getFromTime(), n.getClassRoom());
                }
            });
            return ResponseEntity.ok(map);
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

    @Cacheable(value = "group", key = "#name")
    public Long getGroupIdByName(String name){
        RestTemplate restTemplate = restTemplateObjectProvider.getIfAvailable();
        return restTemplate.getForObject("http://localhost:8072/group/v1/group/{name}", Group.class, name).getId();
    }
}
