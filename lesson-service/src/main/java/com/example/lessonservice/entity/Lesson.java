package com.example.lessonservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    private LocalDate day;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    private Long number;

    @Column(name = "teacher_id")
    private Long teacherId;

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
