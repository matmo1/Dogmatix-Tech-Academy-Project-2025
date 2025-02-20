package com.dogmatix.homeworkplatform.RolesAndPermitions.Model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "user_id", length = 36, updatable = false, nullable = false)
    private UUID userId;

    @Column(name="email")
    private String username;

    @Column(name="password_hash")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

    public UUID getId() {
        return userId;
    }

    public void setId(UUID id) {
        this.userId = id;
    }

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

    public Set<Role> getRoles() {
        return role;
    }

    public void setRoles(Set<Role> roles) {
        this.role = roles;
    }
}
