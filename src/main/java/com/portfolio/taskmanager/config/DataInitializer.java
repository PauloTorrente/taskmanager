package com.portfolio.taskmanager.config;

import com.portfolio.taskmanager.model.Task;
import com.portfolio.taskmanager.model.TaskPriority;
import com.portfolio.taskmanager.model.TaskStatus;
import com.portfolio.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(TaskRepository taskRepository) {
        return args -> {
            // Only populate if database is empty
            if (taskRepository.count() == 0) {
                log.info("Populating database with sample data...");

                // Sample task 1: environment setup
                taskRepository.save(Task.builder()
                        .title("Setup development environment")
                        .description("Install JDK 17, Maven and IntelliJ IDEA")
                        .status(TaskStatus.DONE)
                        .priority(TaskPriority.HIGH)
                        .dueDate(LocalDateTime.now().minusDays(2))
                        .build());

                // Sample task 2: project structure
                taskRepository.save(Task.builder()
                        .title("Create Spring Boot project structure")
                        .description("Define packages, dependencies and layered architecture")
                        .status(TaskStatus.DONE)
                        .priority(TaskPriority.HIGH)
                        .build());

                // Sample task 3: API endpoints
                taskRepository.save(Task.builder()
                        .title("Implement API endpoints")
                        .description("Full CRUD with GET, POST, PUT, PATCH and DELETE")
                        .status(TaskStatus.IN_PROGRESS)
                        .priority(TaskPriority.HIGH)
                        .dueDate(LocalDateTime.now().plusDays(1))
                        .build());

                // Sample task 4: unit tests
                taskRepository.save(Task.builder()
                        .title("Write unit tests")
                        .description("Tests for Service and Controller with JUnit 5 and Mockito")
                        .status(TaskStatus.TODO)
                        .priority(TaskPriority.MEDIUM)
                        .dueDate(LocalDateTime.now().plusDays(3))
                        .build());

                // Sample task 5: documentation
                taskRepository.save(Task.builder()
                        .title("Document API with Swagger")
                        .description("Add descriptions to endpoints and models")
                        .status(TaskStatus.IN_PROGRESS)
                        .priority(TaskPriority.MEDIUM)
                        .build());

                // Sample task 6: deployment
                taskRepository.save(Task.builder()
                        .title("Deploy on Railway or Render")
                        .description("Put the application online for portfolio")
                        .status(TaskStatus.TODO)
                        .priority(TaskPriority.LOW)
                        .dueDate(LocalDateTime.now().plusDays(7))
                        .build());

                log.info("✅ {} sample tasks created.", taskRepository.count());
            }
        };
    }
}
