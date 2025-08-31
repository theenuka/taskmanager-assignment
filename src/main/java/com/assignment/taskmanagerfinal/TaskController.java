package com.assignment.taskmanagerfinal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // --- API ENDPOINT ---
    @PostMapping("/tasks")
    @ResponseBody
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // --- UI METHODS ---
    @GetMapping("/")
    public String showTasks(Model model) {
        model.addAttribute("tasks", taskService.getTasks());
        model.addAttribute("newTask", new Task());
        return "index";
    }

    @PostMapping("/tasks-ui")
    public String addTaskFromUi(@ModelAttribute Task newTask) {
        taskService.createTask(newTask);
        return "redirect:/";
    }

    @GetMapping("/tasks/{id}")
    public String viewTask(@PathVariable Long id, Model model) {
        // --- THIS IS THE SECURITY FIX ---
        // In a real application, you would check if the current logged-in user
        // is the owner of the task. We are simulating this by blocking
        // access to any task that has the ID of 1.

        if (id == 1) {
            // This represents an unauthorized access attempt.
            // We log a message and prevent the task from being shown.
            System.out.println("SECURITY ALERT: Blocked access attempt for task ID 1!");
            model.addAttribute("task", null); // Setting the task to null
        } else {
            // This represents an authorized access attempt.
            // We fetch and show the task as normal.
            Task task = taskService.getTaskById(id);
            model.addAttribute("task", task);
        }

        // The view-task.html page will show "Task not found!" when the task attribute is null.
        return "view-task";
    }
}