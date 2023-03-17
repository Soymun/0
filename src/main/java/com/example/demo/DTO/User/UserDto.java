package com.example.demo.DTO.User;

import com.example.demo.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String email;

    private Role role;

    private Long groupId;

    private String name;

    private String surname;

    private String patronymic;

    private LocalDate birthday;
}
