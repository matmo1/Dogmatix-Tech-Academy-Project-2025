package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.User;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.ClassRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dogmatix.homeworkplatform.RolesAndPermitions.DTOs.LoginRequest;
import com.dogmatix.homeworkplatform.RolesAndPermitions.DTOs.RegisterRequest;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Service.UserService;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/students/{username}")
    public Optional<User> getStudents(@PathVariable String username){
        return userRepository.findByUsername(username);


    }

}
