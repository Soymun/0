package com.example.universityservice.dto.classroom;

import com.example.universityservice.entity.University;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoomDto {

    private Long id;

    private String classRoom;

    private University university;
}
