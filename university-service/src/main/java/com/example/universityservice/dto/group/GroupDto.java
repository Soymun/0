package com.example.universityservice.dto.group;

import com.example.universityservice.dto.university.UniversityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {

    private Long id;

    private String name;

    private Long numberCourse;

    private UniversityDto university;
}
