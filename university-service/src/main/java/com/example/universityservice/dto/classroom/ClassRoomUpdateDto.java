package com.example.universityservice.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoomUpdateDto {

    private Long id;

    private String classRoom;
}
