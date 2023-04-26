package com.example.lessonservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.time.LocalTime;

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

    private LocalTime fromTime;

    private LocalTime toTime;

    private Long number;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "class_room_id")
    private Long classRoomId;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_room_id", insertable = false, updatable = false)
    private ClassRoom classRoom;

    @Column(name = "type_lesson_id")
    private Long typeLessonId;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_lesson_id", insertable = false, updatable = false)
    private TypeOfLesson typeOfLesson;
}
