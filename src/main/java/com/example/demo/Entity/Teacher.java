package com.example.demo.Entity;


import lombok.*;

import javax.persistence.*;

//y
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teacherName;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Teacher(String teacherName) {
        this.teacherName = teacherName;
    }
}
