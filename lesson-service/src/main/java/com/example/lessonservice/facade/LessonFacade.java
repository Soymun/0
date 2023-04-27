package com.example.lessonservice.facade;


import com.example.lessonservice.dto.ClassRoom;
import com.example.lessonservice.dto.Course;
import com.example.lessonservice.dto.Lesson.*;
import com.example.lessonservice.entity.LessonGroup;
import com.example.lessonservice.service.Impl.LessonServiceImpl;
import com.example.lessonservice.service.Impl.RestServiceTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class LessonFacade {

    private final LessonServiceImpl lessonService;

    private final RestServiceTemplate restServiceTemplate;

    public ResponseEntity<?> saveFromFile(MultipartFile multipartFile, Long universityId) throws IOException {
        Map<LessonGroup, String> map = lessonService.saveLessonFromFile(multipartFile, universityId);
        map.entrySet().stream().parallel().forEach(n -> {
            LessonGroup lessonGroup = n.getKey();
            lessonGroup.setGroupId(restServiceTemplate.getGroupIdByName(n.getValue().trim().toUpperCase()).getId());
            lessonService.saveLessonGroup(lessonGroup);
        });
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<?> saveLesson(LessonCreateDto lessonCreateDto) {
        lessonCreateDto.getWeeks().stream().parallel().forEach(n -> {
            LessonGroup lessonGroup = new LessonGroup();
            lessonGroup.setLessonId(lessonService.saveLesson(lessonCreateDto, n, lessonCreateDto.getDay()));
            lessonGroup.setGroupId(lessonCreateDto.getGroupId());
            lessonService.saveLessonGroup(lessonGroup);
        });
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<?> getLesson(GetLessonDto getLessonDto) {
        List<LessonDto> lessonDtos = lessonService.getLesson(getLessonDto.getGroupId(), getLessonDto.getDay(), getLessonDto.getDay2());
        return ResponseEntity.ok(convert(lessonDtos));
    }

    public ResponseEntity<?> getLessonForTeacher(GetLessonTeacher getLessonDto) {
        List<LessonDto> lessonDtos = lessonService.getLessonForTeacher(getLessonDto.getId(), getLessonDto.getDay(), getLessonDto.getDay2());
        Map<LocalDate, ListOutputLessonDto> lessonByDay = convert(lessonDtos);
        Map<LocalDate, List<OutputLessonTeacherDto>> result = new TreeMap<>(LocalDate::compareTo);
        for (Map.Entry<LocalDate, ListOutputLessonDto> entry: lessonByDay.entrySet()){
            List<OutputLessonTeacherDto> listResult = new ArrayList<>();
            for (Map.Entry<OutputLessonDto, OutputLessonDto> day: entry.getValue().getOutputLessonDtos().entrySet()){
                OutputLessonTeacherDto outputLessonTeacherDto = new OutputLessonTeacherDto(day.getValue(), restServiceTemplate.getGroupById(day.getValue().getId()));
                listResult.add(outputLessonTeacherDto);
            }
            listResult = listResult.stream().sorted(Comparator.comparing(o -> o.getOutputLessonDto().getFromTime())).toList();
            result.put(entry.getKey(), listResult);
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> getLessonForUpdate(UpdateLessonDto updateLessonDto) {
        Course course = restServiceTemplate.getCourse(updateLessonDto.getCourseId());
        List<LessonDto> lessonDtos = lessonService
                .getUpdateLesson(updateLessonDto.getGroupId(), course.getId(), updateLessonDto.getTypeId())
                .stream()
                .filter(n -> n.getDay().getDayOfWeek().equals(updateLessonDto.getLocalDateTime().getDayOfWeek()))
                .toList();
        GetUpdateLessonDto getUpdateLessonDto = new GetUpdateLessonDto();
        lessonDtos.forEach(n -> {
            getUpdateLessonDto.setCourse(n.getCourseId().equals(course.getId())?course: restServiceTemplate.getCourse(n.getCourseId()));
            getUpdateLessonDto.setType(n.getType());
            getUpdateLessonDto.setTeacher(restServiceTemplate.getTeacher(n.getTeacherId()));
            getUpdateLessonDto.setClassRoom(restServiceTemplate.getClassRoomById(n.getClassRoomId()));
            getUpdateLessonDto.setFromTime(n.getFromTime());
            getUpdateLessonDto.setToTime(n.getToTime());
            getUpdateLessonDto.setNumber(n.getNumber());
            getUpdateLessonDto.getIds().add(n.getId());
            getUpdateLessonDto.setDay(n.getDay());
            getUpdateLessonDto.setType(n.getType());
        });
        return ResponseEntity.ok(getUpdateLessonDto);
    }

    public ResponseEntity<?> patchLesson(LessonToUpdateDto lesson) {
        lessonService.updateLesson(lesson);
        return ResponseEntity.noContent().build();
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
            Map<LocalTime, ClassRoom> map = new HashMap<>();
            lessonDtos.forEach(n -> {
                if (!map.containsKey(n.getFromTime())) {
                    map.put(n.getFromTime(), restServiceTemplate.getClassRoomById(n.getClassRoomId()));
                }
            });
            return ResponseEntity.ok(map);
        }
    }

    private Map<LocalDate, ListOutputLessonDto> convert(List<LessonDto> lessonDtos){
        Map<LocalDate, ListOutputLessonDto> map = new TreeMap<>(LocalDate::compareTo);
        lessonDtos.forEach(n -> {
                    ListOutputLessonDto lessonDto = map.getOrDefault(n.getDay(), new ListOutputLessonDto());

                    OutputLessonDto outputLessonDto = new OutputLessonDto(n, restServiceTemplate.getCourse(n.getCourseId()), restServiceTemplate.getTeacher(n.getTeacherId()), restServiceTemplate.getClassRoomById(n.getClassRoomId()));

                    Map<OutputLessonDto,OutputLessonDto> thisLessonDay = lessonDto.getOutputLessonDtos();

                    OutputLessonDto outputLessonDtoFromMap = thisLessonDay.get(outputLessonDto);

                    if(outputLessonDtoFromMap == null){
                        thisLessonDay.put(outputLessonDto, outputLessonDto);
                    }

                    else {
                        if(outputLessonDtoFromMap.getNumber().stream().anyMatch(o -> Math.abs(o.intValue() - outputLessonDto.getNumber().get(0).intValue()) == 1)){

                            outputLessonDtoFromMap.setFromTime(outputLessonDto.getFromTime().isBefore(outputLessonDtoFromMap.getFromTime())? outputLessonDto.getFromTime():outputLessonDtoFromMap.getFromTime());
                            outputLessonDtoFromMap.setToTime(outputLessonDto.getToTime().isAfter(outputLessonDtoFromMap.getToTime())? outputLessonDto.getToTime():outputLessonDtoFromMap.getToTime());
                            thisLessonDay.put(outputLessonDtoFromMap, outputLessonDtoFromMap);
                        }
                        else {
                            thisLessonDay.put(outputLessonDto, outputLessonDto);
                        }
                    }
                    lessonDto.setOutputLessonDtos(thisLessonDay);
                    map.put(n.getDay(), lessonDto);
                }
        );
        return map;
    }
}
