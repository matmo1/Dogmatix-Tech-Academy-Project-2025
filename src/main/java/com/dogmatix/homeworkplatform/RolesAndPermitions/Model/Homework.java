package com.dogmatix.homeworkplatform.RolesAndPermitions.Model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "homeworks")
public class Homework {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "homework_id", length = 36, updatable = false, nullable = false)
    private UUID homeworkId;

    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "class_id", nullable = false, length = 36)
    private UUID classId;

    @Column(name = "created_by", nullable = false, length = 36)
    private UUID createdBy;

    @Column(name = "is_published", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isPublished;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt;

    // Getters and setters
    public UUID getHomeworkId() {
        return homeworkId;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getDeadline() {
        return deadline;
    }
    
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    
    public UUID getClassId() {
        return classId;
    }

    public UUID getCreatedBy(){
        return createdBy;
    }

    public boolean getIsPublished(){
        return isPublished;
    }

    public void setIsPublished(boolean b){
        this.isPublished = b;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public void setCreatedBy(UUID id) {
        this.createdBy = id;
    }
}
