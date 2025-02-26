package com.dogmatix.homeworkplatform.RolesAndPermitions.DTOs;


import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Role;

public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
