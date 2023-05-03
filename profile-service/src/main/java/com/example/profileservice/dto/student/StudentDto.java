package com.example.profileservice.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;

    private String name;

    private String surname;

    private String patronymic;

    private String email;

    private LocalDate birthday;

    private Long groupId;
}
