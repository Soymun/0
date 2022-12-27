package com.example.demo.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teacherName;

    @Column(name = "userId")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    public Teacher(String teacherName) {
        this.teacherName = teacherName;
    }
}
