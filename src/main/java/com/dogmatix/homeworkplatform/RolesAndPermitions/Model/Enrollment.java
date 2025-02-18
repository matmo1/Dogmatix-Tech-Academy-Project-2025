package com.dogmatix.homeworkplatform.RolesAndPermitions.Model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollments")

public class Enrollment {

    @Id
    @Column(name = "enrollment_id", updatable = false, nullable = false)
    private UUID enrollmentId = UUID.randomUUID(); // Default UUID()

    @Column(name = "class_id", nullable = false)
    private UUID classId; // References class_service_db.classes.class_id

    @Column(name = "user_id", nullable = false)
    private UUID userId; // References user_service_db.users.user_id

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role; // Enum: 'student' or 'teacher'

    @Column(name = "enrolled_at", nullable = false, updatable = false)
    private LocalDateTime enrolledAt = LocalDateTime.now(); // Default CURRENT_TIMESTAMP

    public enum Role {
        STUDENT, TEACHER
    }

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }
}