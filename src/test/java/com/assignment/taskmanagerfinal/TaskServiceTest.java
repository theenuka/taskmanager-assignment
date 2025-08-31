package com.assignment.taskmanagerfinal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Test
    void shouldCreateTaskSuccessfully() {
        // Arrange
        TaskService taskService = new TaskService();
        Task newTask = new Task(null, "Learn TDD", false);

        // Act
        Task createdTask = taskService.createTask(newTask);

        // Assert
        assertNotNull(createdTask, "Created task should not be null");
        assertNotNull(createdTask.getId(), "Created task should have an ID");
        assertEquals("Learn TDD", createdTask.getTitle(), "Title should match");
        assertFalse(createdTask.isCompleted(), "Task should be incomplete by default");
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        // Arrange
        TaskService taskService = new TaskService();
        Task invalidTask = new Task(null, null, false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(invalidTask);
        }, "Should throw exception for null title");
    }

    @Test
    void shouldThrowExceptionWhenTitleIsEmpty() {
        // Arrange
        TaskService taskService = new TaskService();
        Task invalidTask = new Task(null, "", false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(invalidTask);
        }, "Should throw exception for empty title");
    }
}