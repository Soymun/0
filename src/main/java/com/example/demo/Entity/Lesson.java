package com.example.demo.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lesson;

    private LocalDateTime day;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private Long number;

    private String teacherName;

    private String classRoom;
}
