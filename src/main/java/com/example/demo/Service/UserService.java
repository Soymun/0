package com.example.demo.Service;

import com.example.demo.DTO.UserDto;
import com.example.demo.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto getUserById(Long id);

    UserDto getUserByUsername(String username);

    UserDto updateUser(UserDto userDto);

    void deleteUser(Long id);

    UserDto saveUser(User user);

    List<UserDto> getUserByGroupId(Long id);
}
