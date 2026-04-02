package com.portfolio.taskmanager.repository;

import com.portfolio.taskmanager.model.Task;
import com.portfolio.taskmanager.model.TaskPriority;
import com.portfolio.taskmanager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this as a Spring Data repository
public interface TaskRepository extends JpaRepository<Task, Long> { // Provides CRUD operations

    // Spring generates: SELECT * FROM tasks WHERE status = ?
    List<Task> findByStatus(TaskStatus status);

    // Spring generates: SELECT * FROM tasks WHERE priority = ?
    List<Task> findByPriority(TaskPriority priority);

    // Spring generates: SELECT * FROM tasks WHERE status = ? AND priority = ?
    List<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority);

    // Custom JPQL query - search by title or description (case insensitive)
    @Query("SELECT t FROM Task t WHERE " +
           "LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Task> searchByKeyword(@Param("keyword") String keyword);

    // Count tasks by status - useful for dashboards and reports
    long countByStatus(TaskStatus status);
}
