package com.assignment.taskmanagerfinal;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public Task createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty.");
        }
        long newId = counter.incrementAndGet();
        Task taskWithId = new Task(newId, task.getTitle(), task.isCompleted());
        tasks.add(taskWithId);
        return taskWithId;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}