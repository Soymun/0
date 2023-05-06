package com.example.courseservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Long numberOfCourse;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "university_Id")
    private Long universityId;

    public Courses(String name) {
        this.name = name;
    }
}
