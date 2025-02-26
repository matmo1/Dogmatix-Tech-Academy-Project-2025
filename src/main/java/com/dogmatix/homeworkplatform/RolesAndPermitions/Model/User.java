package com.dogmatix.homeworkplatform.RolesAndPermitions.Model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "user_id", length = 36, updatable = false, nullable = false)
    private UUID userId;

    @Column(name="email")
    private String username;

    @Column(name="password_hash")
    private String password;

    @Column(columnDefinition = "ENUM('STUDENT', 'ADMIN', 'TEACHER')")
    @Enumerated(EnumType.STRING)
    private Role role;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + getRole().name().toUpperCase());
        return Stream.of(auth).toList();
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
