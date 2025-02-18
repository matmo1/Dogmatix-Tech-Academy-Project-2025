package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Grade;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.Role;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.User;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.GradeRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final GradeRepository gradeRepository;

    public AdminController(UserRepository userRepository, GradeRepository gradeRepository) {
        this.userRepository = userRepository;
        this.gradeRepository = gradeRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/manage-users/role/{userId}")
    public void assignUserRole(@PathVariable UUID userId, @RequestBody Set<Role> roles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/manage-users/{userId}")
    public void assignUserEmail(@PathVariable UUID userId, @RequestParam String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(username);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/manage-users/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/manage-users/homework/{userId}/{submissionId}")
    public void updateGrade(@PathVariable UUID userId, @PathVariable UUID submissionId, @RequestParam BigDecimal score) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Grade grade = gradeRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        grade.setScore(score);
        gradeRepository.save(grade);
    }


}

