package com.example.demo.Entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "courses_id")
    private Long coursesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courses_id", insertable = false, updatable = false)
    private Courses courses;

    private LocalDateTime day;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private Long number;

    @Column(name = "teacher_id")
    private Long teacherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private Teacher teacher;

    @Column(name = "class_room_id")
    private Long classRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_room_id", insertable = false, updatable = false)
    private ClassRoom classRoom;

    @Column(name = "type_lesson_id")
    private Long typeLessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_lesson_id", insertable = false, updatable = false)
    private TypeOfLesson typeOfLesson;
}
