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
}