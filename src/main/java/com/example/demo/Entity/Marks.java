package com.example.demo.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @Column(name = "lessonId")
    private Long lessonId;

    @ManyToOne
    @JoinColumn(name = "lessonId", insertable = false, updatable = false)
    private LessonName lessonName;

    private Long mark;
}
