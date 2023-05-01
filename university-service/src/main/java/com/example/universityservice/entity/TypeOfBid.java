package com.example.universityservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "type_bid")
public class TypeOfBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "university_id")
    private Long universityId;

    @ManyToOne
    @JoinColumn(name = "university_id", insertable = false, updatable = false)
    private University university;

    private String name;
}
