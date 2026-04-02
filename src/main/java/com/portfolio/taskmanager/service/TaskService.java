package com.portfolio.taskmanager.service;

import com.portfolio.taskmanager.dto.TaskDTO;
import com.portfolio.taskmanager.exception.TaskNotFoundException;
import com.portfolio.taskmanager.model.Task;
import com.portfolio.taskmanager.model.TaskPriority;
import com.portfolio.taskmanager.model.TaskStatus;
import com.portfolio.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // Provides logger
@Service // Marks as Spring service component
@RequiredArgsConstructor // Constructor injection
public class TaskService {

    private final TaskRepository taskRepository;

    // Find all tasks with optional filters
    @Transactional(readOnly = true) // Read-only transaction for performance
    public List<TaskDTO.Response> findAll(TaskStatus status, TaskPriority priority) {
        log.info("Finding tasks - status: {}, priority: {}", status, priority);

        List<Task> tasks;

        // Apply filters based on provided parameters
        if (status != null && priority != null) {
            tasks = taskRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else if (priority != null) {
            tasks = taskRepository.findByPriority(priority);
        } else {
            tasks = taskRepository.findAll();
        }

        // Convert entities to DTOs using stream API
        return tasks.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Find task by ID - throws exception if not found
    @Transactional(readOnly = true)
    public TaskDTO.Response findById(Long id) {
        log.info("Finding task with id: {}", id);
        Task task = getTaskOrThrow(id);
        return toResponse(task);
    }

    // Search tasks by keyword in title or description
    @Transactional(readOnly = true)
    public List<TaskDTO.Response> search(String keyword) {
        log.info("Searching tasks with keyword: {}", keyword);
        return taskRepository.searchByKeyword(keyword)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Create a new task
    @Transactional
    public TaskDTO.Response create(TaskDTO.CreateRequest request) {
        log.info("Creating new task: {}", request.getTitle());

        // Build task with default values
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority() != null ? request.getPriority() : TaskPriority.MEDIUM) // Default MEDIUM
                .status(TaskStatus.TODO) // Default TODO
                .dueDate(request.getDueDate())
                .build();

        Task saved = taskRepository.save(task);
        log.info("Task created with id: {}", saved.getId());
        return toResponse(saved);
    }

    // Update an existing task
    @Transactional
    public TaskDTO.Response update(Long id, TaskDTO.UpdateRequest request) {
        log.info("Updating task with id: {}", id);

        Task task = getTaskOrThrow(id);

        // Update only received fields
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());

        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    // Update only the status of a task
    @Transactional
    public TaskDTO.Response updateStatus(Long id, TaskStatus newStatus) {
        log.info("Updating status of task {} to {}", id, newStatus);

        Task task = getTaskOrThrow(id);
        task.setStatus(newStatus);
        return toResponse(taskRepository.save(task));
    }

    // Delete a task by ID
    @Transactional
    public void delete(Long id) {
        log.info("Deleting task with id: {}", id);
        Task task = getTaskOrThrow(id);
        taskRepository.delete(task);
    }

    // Get statistics summary of tasks
    @Transactional(readOnly = true)
    public TaskDTO.Summary getSummary() {
        return TaskDTO.Summary.builder()
                .total(taskRepository.count())
                .todo(taskRepository.countByStatus(TaskStatus.TODO))
                .inProgress(taskRepository.countByStatus(TaskStatus.IN_PROGRESS))
                .done(taskRepository.countByStatus(TaskStatus.DONE))
                .cancelled(taskRepository.countByStatus(TaskStatus.CANCELLED))
                .build();
    }

    // Private helper methods

    // Find task by ID or throw TaskNotFoundException - DRY principle
    private Task getTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    // Convert Task entity to Response DTO
    private TaskDTO.Response toResponse(Task task) {
        return TaskDTO.Response.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .dueDate(task.getDueDate())
                .build();
    }
}
