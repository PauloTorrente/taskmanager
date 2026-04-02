package com.portfolio.taskmanager.model;

// Task status options - using enum ensures only valid values are accepted
public enum TaskStatus {
    TODO,        // To do
    IN_PROGRESS, // In progress
    DONE,        // Completed
    CANCELLED    // Cancelled
}
