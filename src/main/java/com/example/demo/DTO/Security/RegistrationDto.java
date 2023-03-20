package com.example.demo.DTO.Security;

import lombok.Data;

@Data
public class RegistrationDto {

    private String username;

    private String password;

    private Long groupId;
}
