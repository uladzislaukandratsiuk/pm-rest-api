package com.demo.rest.api.service;

import com.demo.rest.api.dao.TaskDao;
import com.demo.rest.api.entity.Task;
import com.demo.rest.api.exception.CustomEntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    @Transactional
    public List<Task> getTasks() {
        List<Task> tasks = taskDao.findAll();
        if (tasks == null || tasks.isEmpty())
            throw new CustomEntityNotFoundException("No Tasks data found!");
        return tasks;
    }

    @Override
    @Transactional
    public Optional<Task> getTask(Long id) {
        Optional<Task> task = taskDao.findById(id);
        if (task.isEmpty())
            throw new CustomEntityNotFoundException("Task with id=" + id + " not found!");
        return task;
    }

    @Override
    @Transactional
    public void saveTask(Task task) {
        taskDao.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Optional<Task> task = taskDao.findById(id);
        if (task.isEmpty())
            throw new CustomEntityNotFoundException("Task with id=" + id + " not found!");
        taskDao.deleteById(id);
    }
}
