package com.example.universityservice.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateDto {

    private String name;

    private Long universityId;

    private Long numberCourse;
}
