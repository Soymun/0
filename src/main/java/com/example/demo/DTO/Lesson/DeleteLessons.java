package com.example.demo.DTO.Lesson;

import lombok.Data;

import java.util.List;

@Data
public class DeleteLessons {

    private List<Long> lessonIds;
}
