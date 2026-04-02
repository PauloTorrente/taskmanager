package com.portfolio.taskmanager.exception;

// Custom exception for when a task is not found
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}
