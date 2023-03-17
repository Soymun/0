package com.example.demo.DTO.Marks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksDto {

    private Long id;

    private Long userId;

    private Long coursesId;

    private Long mark;
}
