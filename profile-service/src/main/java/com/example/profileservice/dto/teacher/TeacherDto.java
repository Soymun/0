package com.example.profileservice.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    private Long id;

    private String name;

    private String nameTeacher;

    private String surnameTeacher;

    private String patronymicTeacher;

    private Long universityId;

    private String email;
}
