package com.example.demo.Controller;


import com.example.demo.DTO.LoginDto;
import com.example.demo.DTO.RegistrationDto;
import com.example.demo.Facade.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ytsu")
public class AuthController {

    private final AuthFacade authFacade;

    @Autowired
    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto){
        return authFacade.registration(registrationDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        return authFacade.login(loginDto);
    }

    @PostMapping("/teacher/registration")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> registrationTeacher(@RequestParam Long id, @RequestParam String name){
        return authFacade.registrationTeacher(id, name);
    }

    @PostMapping("/admin/registration/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> registrationAdmin(@PathVariable Long id){
        return authFacade.registrationAdmin(id);
    }
}
