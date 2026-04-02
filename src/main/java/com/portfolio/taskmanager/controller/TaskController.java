package com.portfolio.taskmanager.controller;

import com.portfolio.taskmanager.dto.TaskDTO;
import com.portfolio.taskmanager.model.TaskPriority;
import com.portfolio.taskmanager.model.TaskStatus;
import com.portfolio.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks") // API versioning
@RequiredArgsConstructor // Generates constructor with required args
@Tag(name = "Tasks", description = "Task management endpoints") // Swagger tag
public class TaskController {

    private final TaskService taskService; // Dependency injection via constructor

    // GET endpoints - Read operations

    @GetMapping // List all tasks with optional filters
    @Operation(summary = "List all tasks", description = "Returns task list with optional filters by status and priority")
    public ResponseEntity<List<TaskDTO.Response>> findAll(
            @Parameter(description = "Filter by status") @RequestParam(required = false) TaskStatus status,
            @Parameter(description = "Filter by priority") @RequestParam(required = false) TaskPriority priority
    ) {
        return ResponseEntity.ok(taskService.findAll(status, priority)); // HTTP 200 OK
    }

    @GetMapping("/{id}") // Get task by ID
    @Operation(summary = "Find task by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task found"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskDTO.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @GetMapping("/search") // Search by keyword in title or description
    @Operation(summary = "Search tasks by keyword in title or description")
    public ResponseEntity<List<TaskDTO.Response>> search(
            @Parameter(description = "Keyword to search", required = true) @RequestParam String keyword
    ) {
        return ResponseEntity.ok(taskService.search(keyword));
    }

    @GetMapping("/summary") // Get statistics summary
    @Operation(summary = "Get statistics summary of tasks")
    public ResponseEntity<TaskDTO.Summary> getSummary() {
        return ResponseEntity.ok(taskService.getSummary());
    }

    // POST endpoints - Create operations

    @PostMapping // Create new task
    @Operation(summary = "Create a new task")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    public ResponseEntity<TaskDTO.Response> create(@Valid @RequestBody TaskDTO.CreateRequest request) {
        TaskDTO.Response created = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // HTTP 201 Created
    }

    // PUT endpoints - Full update operations

    @PutMapping("/{id}") // Full update of a task
    @Operation(summary = "Update a task completely")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Task updated"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskDTO.Response> update(@PathVariable Long id, @Valid @RequestBody TaskDTO.UpdateRequest request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    // PATCH endpoints - Partial update operations

    @PatchMapping("/{id}/status") // Update only status
    @Operation(summary = "Update only the task status")
    public ResponseEntity<TaskDTO.Response> updateStatus(
            @PathVariable Long id,
            @Parameter(description = "New status", required = true) @RequestParam TaskStatus status
    ) {
        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }

    // DELETE endpoints - Remove operations

    @DeleteMapping("/{id}") // Delete task by ID
    @Operation(summary = "Delete a task")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Task deleted"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
