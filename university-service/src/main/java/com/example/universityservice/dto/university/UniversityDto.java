package com.example.universityservice.dto.university;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityDto {

    private Long id;

    private String name;

    private String city;

    private String street;

    private String house;

    private Double xCoordinate;

    private Double yCoordinate;
}
