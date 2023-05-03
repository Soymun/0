package com.example.profileservice.dto.teacher;

import lombok.Data;

import java.util.UUID;

@Data
public class TeacherCreateDto {

    private String name;

    private String nameTeacher;

    private String surnameTeacher;

    private Long universityId;

    private String patronymicTeacher;

    private UUID userId;
}
