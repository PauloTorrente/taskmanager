package com.portfolio.taskmanager.dto;

import com.portfolio.taskmanager.model.TaskPriority;
import com.portfolio.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class TaskDTO {

    // DTO for CREATE request (POST /tasks)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {

        @NotBlank(message = "Title is required") // Validation: cannot be blank
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        private String title;

        @Size(max = 500, message = "Description can have at most 500 characters")
        private String description;

        private TaskPriority priority; // Optional, defaults to MEDIUM

        private LocalDateTime dueDate; // Optional due date
    }

    // DTO for UPDATE request (PUT /tasks/{id})
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        private String title;

        @Size(max = 500, message = "Description can have at most 500 characters")
        private String description;

        @NotNull(message = "Status is required") // Status cannot be null
        private TaskStatus status;

        @NotNull(message = "Priority is required") // Priority cannot be null
        private TaskPriority priority;

        private LocalDateTime dueDate;
    }

    // DTO for RESPONSE - what API returns to client
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private TaskStatus status;
        private TaskPriority priority;
        private LocalDateTime createdAt; // Auto-generated
        private LocalDateTime updatedAt; // Auto-generated
        private LocalDateTime dueDate;
    }

    // DTO for statistics summary
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Summary {
        private long total; // Total tasks
        private long todo; // Tasks with TODO status
        private long inProgress; // Tasks with IN_PROGRESS status
        private long done; // Tasks with DONE status
        private long cancelled; // Tasks with CANCELLED status
    }
}
