package com.portfolio.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks") // Maps to "tasks" table in database
@Data // Generates getters, setters, toString, equals, hashCode
@Builder // Builder pattern for object creation
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(nullable = false, length = 100) // Required, max 100 chars
    private String title;

    @Column(length = 500) // Max 500 chars, optional
    private String description;

    @Enumerated(EnumType.STRING) // Stores enum as string in DB
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;

    @Column(name = "created_at", nullable = false, updatable = false) // Cannot be updated
    private LocalDateTime createdAt;

    @Column(name = "updated_at") // Updates automatically
    private LocalDateTime updatedAt;

    @Column(name = "due_date") // Optional due date
    private LocalDateTime dueDate;

    // Runs before saving for the first time
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = TaskStatus.TODO; // Default status
        if (priority == null) priority = TaskPriority.MEDIUM; // Default priority
    }

    // Runs before every update
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
