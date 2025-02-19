package com.dogmatix.homeworkplatform.RolesAndPermitions.Controllers;

import com.dogmatix.homeworkplatform.RolesAndPermitions.Model.*;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.ClassRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.EnrollmentRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.GradeRepository;
import com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final GradeRepository gradeRepository;
    @Autowired
    private final ClassRepository classRepository;
    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    public AdminController(UserRepository userRepository, GradeRepository gradeRepository, ClassRepository classRepository, EnrollmentRepository enrollmenRepository) {
        this.userRepository = userRepository;
        this.gradeRepository = gradeRepository;
        this.classRepository = classRepository;
        this.enrollmentRepository = enrollmenRepository;
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private ClassEntity findClassById(UUID classId) {
        return classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
    }

    private Enrollment findEnrollmentById(UUID enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    private Grade findGradeById(UUID submissionId) {
        return gradeRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/manage-users/role/{userId}")
    public void assignUserRole(@PathVariable UUID userId, @RequestBody Set<Role> roles) {
        User user = findUserById(userId);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/manage-users/{userId}")
    public void assignUserEmail(@PathVariable UUID userId, @RequestParam String username) {
        User user = findUserById(userId);
        user.setUsername(username);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/manage-users/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/manage-users/homework/{userId}/{submissionId}")
    public void updateGrade(@PathVariable UUID userId, @PathVariable UUID submissionId, @RequestParam BigDecimal score) {
        User user = findUserById(userId);

        Grade grade = findGradeById(submissionId);

        if (!grade.getGradeId().equals(user.getId())) {
            throw new RuntimeException("This user does not own the submission");
        }

        grade.setScore(score);
        gradeRepository.save(grade);
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/manage-users/{userId}")
    public void assignClass(@PathVariable UUID userId,
                            @RequestParam UUID classId) {
        User user = findUserById(userId);
        ClassEntity classEntity = findClassById(classId);
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByUserIdAndClassId(userId, classId);

        if (existingEnrollment.isPresent()) {
            throw new RuntimeException("User is already enrolled in this class.");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(user.getId());
        enrollment.setClassId(classEntity.getClassId());
        enrollment.setRole(Enrollment.Role.STUDENT);
        enrollment.setEnrolledAt(LocalDateTime.now());

        enrollmentRepository.save(enrollment);
    }



}

