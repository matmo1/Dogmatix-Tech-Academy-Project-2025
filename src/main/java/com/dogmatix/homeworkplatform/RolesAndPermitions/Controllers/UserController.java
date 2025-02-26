package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import java.util.Map;
import java.util.UUID;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dogmatix.homeworkplatform.RolesAndPermitions.DTOs.LoginRequest;
import com.dogmatix.homeworkplatform.RolesAndPermitions.DTOs.RegisterRequest;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean isValid = userService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (isValid) {
            return ResponseEntity.ok()
            .body(Map.of("status", "success", "message", "Login successful", "role", "STUDENT"));
        } else {
            return ResponseEntity.status(401)
            .body(Map.of("status", "error", "message", "Invalid username or password"));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        boolean isCreated = userService.createUser(registerRequest.getUsername(), 
        registerRequest.getPassword(), 
        registerRequest.getRole());

        if (isCreated) {
            return ResponseEntity.ok().body(Map.of("status", "success", "message", "User registerd successfully"));
        } else {
            return ResponseEntity.ok()
            .body(Map.of("status", "error", "message", "User registration failed"));
        }
    }

    @GetMapping("/classes")
    public ResponseEntity<?> getAllClasses() {
        return ResponseEntity.ok().body(classRepository.findAll());
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<?> getClasses(@PathVariable UUID id) {
        var cls = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        return ResponseEntity.ok().body(cls);
    }


}
