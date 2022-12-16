package com.example.demo.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.JavaBean;
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

    private Long number;

    private String teacherName;

    private String classRoom;

    @ManyToMany
    @JoinTable(name = "lessonGroup", joinColumns = @JoinColumn(name = "groupId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "lessonId", referencedColumnName = "id"))
    private List<Group> group;
}
