package com.demo.rest.api.controller;

import com.demo.rest.api.entity.Task;
import com.demo.rest.api.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/tasks/{taskId}")
    public Optional<Task> getTask(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {
        taskService.saveTask(task);
        return task;
    }

    @PutMapping("/tasks")
    public Task updateTask(@RequestBody Task task) {
        taskService.saveTask(task);
        return task;
    }

    @DeleteMapping("/tasks/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return "Deleted Task with id=" + taskId;
    }
}
