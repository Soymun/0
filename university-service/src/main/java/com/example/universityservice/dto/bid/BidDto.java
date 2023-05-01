package com.example.universityservice.dto.bid;

import com.example.universityservice.dto.Student;
import com.example.universityservice.dto.type.TypeOfBidDto;
import com.example.universityservice.dto.university.UniversityDto;
import com.example.universityservice.entity.Status;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidDto {

    private Long id;

    private Student student;

    private TypeOfBidDto type;

    private UniversityDto university;

    private Status status;
}
