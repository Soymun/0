package com.example.universityservice.dto.group;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupUpdateDto {

    private Long id;

    private String name;

    private Long numberCourse;
}
