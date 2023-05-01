package com.example.universityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Long id;

    private String name;

    private String surname;

    private String patronymic;

    private LocalDate birthday;

    private Long groupId;
}
