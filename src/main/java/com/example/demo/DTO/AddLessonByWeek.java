package com.example.demo.DTO;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddLessonByWeek {

    private List<Long> weeks;

    private String day;

    private Long groupId;

    private String lesson;

    private String classRoom;

    private String teacher;

    private Long number;

    private String type;
}
