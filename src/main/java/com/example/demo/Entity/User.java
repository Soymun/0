package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String patronymic;

    private LocalDate birthday;

    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Group group;

    @Column(name = "group_id")
    private Long groupId;
}
