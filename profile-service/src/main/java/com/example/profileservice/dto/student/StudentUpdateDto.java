package com.example.profileservice.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpdateDto {

    private Long id;

    private String name;

    private String surname;

    private String patronymic;

    @JsonFormat(pattern = "yyyy:mm:dd")
    private LocalDate birthday;

    private Long groupId;
}
