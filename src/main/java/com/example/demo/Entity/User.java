package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Role role;

    @ManyToOne
    @JoinColumn(name = "groupId", insertable = false, updatable = false)
    private Group group;

    @Column(name = "groupId")
    private Long groupId;
}
