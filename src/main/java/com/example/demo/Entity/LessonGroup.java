package com.example.demo.Entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "lessonGroup")
public class LessonGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groupId")
    private Long groupId;

    @Column(name = "lessonId")
    private Long lessonId;

    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "id", insertable = false, updatable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "lessonId", referencedColumnName = "id", insertable = false, updatable = false)
    private Lesson lesson;
}
