package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dogmatix.homeworkplatform.RolesAndPermitions.DTOs.LoginRequest;
import com.dogmatix.homeworkplatform.RolesAndPermitions.DTOs.RegisterRequest;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:80")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean isValid = userService.validateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (isValid) {
            return ResponseEntity.ok()
            .body(Map.of("status", "success", "message", "Login successful"));
        } else {
            return ResponseEntity.status(401)
            .body(Map.of("status", "error", "message", "Invalid username or password"));
        }
    }
    
    @CrossOrigin(origins = "http://localhost:80")
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
    
}
