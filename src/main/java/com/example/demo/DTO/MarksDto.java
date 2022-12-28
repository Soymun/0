package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksDto {

    private Long id;

    private Long userId;

    private Long lessonId;

    private Long mark;
}
