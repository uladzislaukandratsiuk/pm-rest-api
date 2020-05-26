package com.demo.rest.api.service;

import com.demo.rest.api.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getTasks();

    Optional<Task> getTask(Long id);

    void saveTask(Task task);

    void deleteTask(Long id);
}
