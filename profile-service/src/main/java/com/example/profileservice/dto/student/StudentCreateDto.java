package com.example.profileservice.dto.student;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateDto {

    private String name;

    private String surname;

    private String patronymic;

    private UUID userId;

    @JsonFormat(pattern = "yyyy:mm:dd")
    private LocalDate birthday;

    private Long groupId;
}
