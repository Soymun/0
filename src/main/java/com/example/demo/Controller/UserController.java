package com.example.demo.Controller;


import com.example.demo.DTO.User.UserDto;
import com.example.demo.Response.ResponseDto;
import com.example.demo.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ytsu")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(userService.getUserById(id)).build());
    }

    @PatchMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(ResponseDto.builder().body(userService.updateUser(null)).build());
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    @GetMapping("/group/users/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getUserByGroupId(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(userService.getUserByGroupId(id)).build());
    }
}
