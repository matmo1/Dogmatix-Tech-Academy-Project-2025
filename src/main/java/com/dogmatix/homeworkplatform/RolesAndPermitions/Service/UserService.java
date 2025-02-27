package com.dogmatix.homeworkplatform.RolesAndPermitions.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Role;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.User;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    public boolean createUser(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            System.out.println("User already exists: " + username);
            return false;
        }

        System.out.println("Creating user: " + username + " with role: " + role);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role.toUpperCase()); // Store role in uppercase

        userRepository.save(user);
        System.out.println("User saved successfully!");

        return true;
    }

}
