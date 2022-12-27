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

    @Column(name = "lessonsNameId")
    private Long lessonsNameId;

    @ManyToOne
    @JoinColumn(name = "lessonsNameId", insertable = false, updatable = false)
    private LessonName lesson;

    private LocalDateTime day;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private Long number;

    @Column(name = "teacherId")
    private Long teacherId;

    @ManyToOne
    @JoinColumn(name = "teacherId", insertable = false, updatable = false)
    private Teacher teacher;

    @Column(name = "classRoomId")
    private Long classRoomId;

    @ManyToOne
    @JoinColumn(name = "classRoomId", insertable = false, updatable = false)
    private ClassRoom classRoom;

    @Column(name = "typeId")
    private Long typeId;

    @ManyToOne
    @JoinColumn(name = "typeId", insertable = false, updatable = false)
    private Type type;
}
