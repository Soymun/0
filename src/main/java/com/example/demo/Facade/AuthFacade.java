package com.example.demo.Facade;


import com.example.demo.DTO.Security.LoginDto;
import com.example.demo.DTO.Security.RegistrationDto;
import com.example.demo.DTO.User.UserDto;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.Teacher;
import com.example.demo.Entity.User;
import com.example.demo.Repositories.TeacherRepository;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Security.JwtTokenProvider;
import com.example.demo.Service.Impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthFacade {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserServiceImpl userService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final TeacherRepository teacherRepository;

    public AuthFacade(JwtTokenProvider jwtTokenProvider, UserServiceImpl userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TeacherRepository teacherRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
    }

    public ResponseEntity<?> registration(RegistrationDto registrationDto){
        UserDto userDto = userService.getUserByEmail(registrationDto.getUsername());
        if(userDto.getId() != null){
            throw new RuntimeException("User already exist");
        }

        User user = new User();
        user.setEmail(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole(Role.USER);
        user.setGroupId(registrationDto.getGroupId());
        return ResponseEntity.ok(userService.saveUser(user).getId());
    }

    public ResponseEntity<?> login(LoginDto loginDto){
        try {
            UserDto userDto = userService.getUserByEmail(loginDto.getUsername());
            if(userDto == null){
                throw new RuntimeException("User not found");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            String token = jwtTokenProvider.createToken(userDto.getEmail(), userDto.getRole());
            Map<String, Object> map = new HashMap<>();
            map.put("id", userDto.getId());
            map.put("role", userDto.getRole());
            map.put("token", token);
            return ResponseEntity.ok(map);
        }
        catch (AuthenticationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> registrationTeacher(Long id, String name){
        UserDto userDto = userService.getUserById(id);
        Teacher teacher = teacherRepository.getTeacherByTeacherName(name).orElse(null);
        if(teacher == null){
            Teacher teacher1 = new Teacher();
            teacher1.setTeacherName(name);
            teacher1.setUserId(id);
            teacherRepository.save(teacher1);
        }
        else {
            teacher.setUserId(id);
            teacherRepository.save(teacher);
        }
        userDto.setRole(Role.TEACHER);
        UserDto userDto1 =null;// userService.updateUser(userDto);
        return ResponseEntity.ok(ResponseDto.builder().body(userDto1).build());
    }

    public ResponseEntity<?> registrationAdmin(Long id){
        UserDto userDto = userService.getUserById(id);
        userDto.setRole(Role.ADMIN);
        UserDto userDto1 =null;// userService.updateUser(userDto);
        return ResponseEntity.ok(ResponseDto.builder().body(userDto1).build());
    }

}
