package com.example.demo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class DeleteLessons {

    private List<Long> lessonIds;
}
