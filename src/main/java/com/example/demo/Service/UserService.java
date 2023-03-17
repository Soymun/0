package com.example.demo.Service;

import com.example.demo.DTO.User.UserDto;
import com.example.demo.DTO.User.UserUpdateDto;
import com.example.demo.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    UserDto updateUser(UserUpdateDto userDto);

    void deleteUser(Long id);

    UserDto saveUser(User user);

    List<UserDto> getUserByGroupId(Long id);
}
