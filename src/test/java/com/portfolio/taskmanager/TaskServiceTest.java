package com.portfolio.taskmanager;

import com.portfolio.taskmanager.dto.TaskDTO;
import com.portfolio.taskmanager.exception.TaskNotFoundException;
import com.portfolio.taskmanager.model.Task;
import com.portfolio.taskmanager.model.TaskPriority;
import com.portfolio.taskmanager.model.TaskStatus;
import com.portfolio.taskmanager.repository.TaskRepository;
import com.portfolio.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito
@DisplayName("TaskService - Unit Tests")
class TaskServiceTest {

    @Mock // Creates a mock of the repository (no real database)
    private TaskRepository taskRepository;

    @InjectMocks // Creates service and injects the mock
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        // Sample task used in tests
        sampleTask = Task.builder()
                .id(1L)
                .title("Test task")
                .description("Test description")
                .status(TaskStatus.TODO)
                .priority(TaskPriority.MEDIUM)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // Find tests

    @Test
    @DisplayName("findById - should return task when ID exists")
    void findById_whenExists_shouldReturnTask() {
        // ARRANGE: configure mock to return the task
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        // ACT: call the method being tested
        TaskDTO.Response result = taskService.findById(1L);

        // ASSERT: verify the result
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test task");
        assertThat(result.getStatus()).isEqualTo(TaskStatus.TODO);
    }

    @Test
    @DisplayName("findById - should throw TaskNotFoundException when ID does not exist")
    void findById_whenNotExists_shouldThrowException() {
        // ARRANGE
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT: verify exception is thrown
        assertThatThrownBy(() -> taskService.findById(99L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("findAll - should return list of tasks")
    void findAll_shouldReturnAllTasks() {
        // ARRANGE
        when(taskRepository.findAll()).thenReturn(List.of(sampleTask));
        // ACT
        List<TaskDTO.Response> result = taskService.findAll(null, null);
        // ASSERT
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test task");
    }

    // Create tests

    @Test
    @DisplayName("create - should create task with TODO status by default")
    void create_shouldSetStatusToTodoByDefault() {
        // ARRANGE
        TaskDTO.CreateRequest request = TaskDTO.CreateRequest.builder()
                .title("New task")
                .description("Description")
                .build();

        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        // ACT
        TaskDTO.Response result = taskService.create(request);
        // ASSERT
        assertThat(result).isNotNull();
        // Verify save was called exactly once
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // Delete tests

    @Test
    @DisplayName("delete - should call delete when task exists")
    void delete_whenExists_shouldCallDelete() {
        // ARRANGE
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        // ACT
        taskService.delete(1L);

        // ASSERT: verify delete was called
        verify(taskRepository, times(1)).delete(sampleTask);
    }

    @Test
    @DisplayName("delete - should throw exception when task does not exist")
    void delete_whenNotExists_shouldThrowException() {
        // ARRANGE
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT
        assertThatThrownBy(() -> taskService.delete(99L))
                .isInstanceOf(TaskNotFoundException.class);

        // Verify delete was NEVER called
        verify(taskRepository, never()).delete(any(Task.class));
    }

    // Summary tests

    @Test
    @DisplayName("getSummary - should return correct counts")
    void getSummary_shouldReturnCorrectCounts() {
        // ARRANGE
        when(taskRepository.count()).thenReturn(5L);
        when(taskRepository.countByStatus(TaskStatus.TODO)).thenReturn(2L);
        when(taskRepository.countByStatus(TaskStatus.IN_PROGRESS)).thenReturn(1L);
        when(taskRepository.countByStatus(TaskStatus.DONE)).thenReturn(2L);
        when(taskRepository.countByStatus(TaskStatus.CANCELLED)).thenReturn(0L);

        // ACT
        TaskDTO.Summary summary = taskService.getSummary();

        // ASSERT
        assertThat(summary.getTotal()).isEqualTo(5L);
        assertThat(summary.getTodo()).isEqualTo(2L);
        assertThat(summary.getInProgress()).isEqualTo(1L);
        assertThat(summary.getDone()).isEqualTo(2L);
        assertThat(summary.getCancelled()).isEqualTo(0L);
    }
}
