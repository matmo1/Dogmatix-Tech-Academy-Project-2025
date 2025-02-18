package com.dogmatix.homeworkplatform.RolesAndPermitions.Model;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "classes")

public class ClassEntity {

    @Id
    @Column(name = "class_id", updatable = false, nullable = false)
    private UUID classId = UUID.randomUUID(); // Default UUID()

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Default CURRENT_TIMESTAMP
}

