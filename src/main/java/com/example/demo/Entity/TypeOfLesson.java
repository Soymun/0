package com.example.demo.Entity;

import lombok.*;

import javax.persistence.*;
//y
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "type_lesson")
public class TypeOfLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    private String description;

    public TypeOfLesson(String type) {
        this.type = type;
    }
}
