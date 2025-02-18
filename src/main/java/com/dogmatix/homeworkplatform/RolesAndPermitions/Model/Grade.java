package com.dogmatix.homeworkplatform.RolesAndPermitions.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "grades", indexes = {
        @Index(name = "idx_grades_submission_id", columnList = "submission_id")
})

public class Grade {

    @Id
    @Column(name = "grade_id", updatable = false, nullable = false)
    private UUID gradeId = UUID.randomUUID(); // Default UUID()

    @Column(name = "submission_id", unique = true, nullable = false)
    private UUID submissionId; // References submission in another DB

    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "graded_by", nullable = false)
    private UUID gradedBy; // References user in another DB

    @Column(name = "graded_at", nullable = false, updatable = false)
    private LocalDateTime gradedAt = LocalDateTime.now(); // Default to CURRENT_TIMESTAMP

    public UUID getGradeId() {
        return gradeId;
    }

    public void setGradeId(UUID gradeId) {
        this.gradeId = gradeId;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public UUID getGradedBy() {
        return gradedBy;
    }

    public void setGradedBy(UUID gradedBy) {
        this.gradedBy = gradedBy;
    }

    public LocalDateTime getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(LocalDateTime gradedAt) {
        this.gradedAt = gradedAt;
    }
}

