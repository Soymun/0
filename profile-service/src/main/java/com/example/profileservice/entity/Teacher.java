package com.example.profileservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long universityId;

    private String nameTeacher;

    private String surnameTeacher;

    private String patronymicTeacher;

    private String email;

    @Column(name = "user_id")
    private UUID userId;

}
