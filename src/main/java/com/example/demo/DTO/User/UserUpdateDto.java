package com.example.demo.DTO.User;

import com.example.demo.Entity.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateDto {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String patronymic;

    private LocalDate birthday;

    private Role role;

    private Long groupId;
}
