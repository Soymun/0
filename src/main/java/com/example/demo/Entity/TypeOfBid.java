package com.example.demo.Entity;

import lombok.*;

import javax.persistence.*;
//y
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

    private String name;
}
