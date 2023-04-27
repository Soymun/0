package com.example.lessonservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoom {

    private Long id;

    private String classRoom;

    private Long universityId;
}