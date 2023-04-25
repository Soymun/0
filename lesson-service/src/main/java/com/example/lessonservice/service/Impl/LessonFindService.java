package com.example.lessonservice.service.Impl;

import com.example.lessonservice.entity.ClassRoom;
import com.example.lessonservice.entity.Lesson;
import com.example.lessonservice.entity.TypeOfLesson;
import com.example.lessonservice.entity.Week;
import com.example.lessonservice.repositories.ClassRoomRepository;
import com.example.lessonservice.repositories.TypeRepository;
import com.example.lessonservice.repositories.WeekRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LessonFindService {

    private final ClassRoomRepository classRoomRepository;

    private final TypeRepository typeRepository;

    private final WeekRepository weekRepository;

    @Cacheable(value = "week", key = "#number")
    public Week getWeekByNumber(Long number) {
        return weekRepository.findById(number).orElseThrow(() -> {
            throw new RuntimeException(String.valueOf(number));
        });
    }

    @Cacheable(value = "classRoom", key = "#name")
    public ClassRoom getClassRoomByName(String name) {
        return classRoomRepository.getClassRoomByClassRoom(name).orElseThrow(() -> {throw new RuntimeException(name);});
    }

    @Cacheable(value = "typeOfLesson", key = "#name")
    public TypeOfLesson getTypeOfLessonByName(String name) {
        return typeRepository.getTypeByType(name).orElseThrow(() -> {throw new RuntimeException(name);});
    }

    public void setDayInWeek(String day, Week week, Lesson lesson){
        switch (day) {
            case "ПОНЕДЕЛЬНИК" -> lesson.setDay(week.getFromWeek());
            case "ВТОРНИК" -> lesson.setDay(week.getFromWeek().plusDays(1));
            case "СРЕДА" -> lesson.setDay(week.getFromWeek().plusDays(2));
            case "ЧЕТВЕРГ" -> lesson.setDay(week.getFromWeek().plusDays(3));
            case "ПЯТНИЦА" -> lesson.setDay(week.getFromWeek().plusDays(4));
            case "СУББОТА" -> lesson.setDay(week.getFromWeek().plusDays(5));
        }
    }

    public void setTimeLesson(Long numbersOfLesson, Lesson lesson){
        switch (numbersOfLesson.intValue()) {
            case 1 -> {
                lesson.setFromTime(lesson.getDay().atStartOfDay().plusHours(8).plusMinutes(30));
                lesson.setToTime(lesson.getDay().atStartOfDay().plusHours(10).plusMinutes(0));
            }
            case 2 -> {
                lesson.setFromTime(lesson.getDay().atStartOfDay().plusHours(10).plusMinutes(10));
                lesson.setToTime(lesson.getDay().atStartOfDay().plusHours(11).plusMinutes(40));
            }
            case 3 -> {
                lesson.setFromTime(lesson.getDay().atStartOfDay().plusHours(11).plusMinutes(50));
                lesson.setToTime(lesson.getDay().atStartOfDay().plusHours(13).plusMinutes(20));
            }
            case 4 -> {
                lesson.setFromTime(lesson.getDay().atStartOfDay().plusHours(12).plusMinutes(20));
                lesson.setToTime(lesson.getDay().atStartOfDay().plusHours(13).plusMinutes(50));
            }
            case 5 -> {
                lesson.setFromTime(lesson.getDay().atStartOfDay().plusHours(14).plusMinutes(0));
                lesson.setToTime(lesson.getDay().atStartOfDay().plusHours(15).plusMinutes(30));
            }
            case 6 -> {
                lesson.setFromTime(lesson.getDay().atStartOfDay().plusHours(15).plusMinutes(40));
                lesson.setToTime(lesson.getDay().atStartOfDay().plusHours(17).plusMinutes(10));
            }
            case 7 -> {
                lesson.setFromTime(lesson.getDay().atStartOfDay().plusHours(17).plusMinutes(30));
                lesson.setToTime(lesson.getDay().atStartOfDay().plusHours(19).plusMinutes(0));
            }
        }
    }
}
