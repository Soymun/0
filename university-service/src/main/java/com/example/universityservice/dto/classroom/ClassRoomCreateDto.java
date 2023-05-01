package com.example.universityservice.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoomCreateDto {

    private String classRoom;

    private Long universityId;
}
