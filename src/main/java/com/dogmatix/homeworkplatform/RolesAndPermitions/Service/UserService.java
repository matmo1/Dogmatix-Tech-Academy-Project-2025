package com.dogmatix.homeworkplatform.RolesAndPermitions.Service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Role;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.User;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public boolean validateUser(String username, String password) {
        Optional<User> userOp = userRepository.findByUsername(username);
        User user = userOp.get();
        return user != null && user.getPassword().equals(password);
    }

    public boolean createUser(String username, 
    String password,  
    Set<Role> role) {
        if (userRepository.findByUsername(username) != null) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(role);
        return true;
    }
}
