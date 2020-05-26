package com.demo.rest.api.service;

import com.demo.rest.api.dao.TaskDao;
import com.demo.rest.api.entity.Task;

import java.util.List;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public List<Task> getTasks() {
        return taskDao.findAll();
    }

    @Override
    public Optional<Task> getTask(Long id) {
        return taskDao.findById(id);
    }

    @Override
    public void saveTask(Task task) {
        taskDao.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskDao.deleteById(id);
    }
}
