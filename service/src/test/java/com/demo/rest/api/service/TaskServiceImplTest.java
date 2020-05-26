package com.demo.rest.api.service;

import com.demo.rest.api.dao.TaskDao;
import com.demo.rest.api.entity.Task;
import com.demo.rest.api.exception.CustomEntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TaskServiceImplTest {

    private static final int ONCE = 1;
    private static final long TEST_TASK_ID = 1L;
    private static Task TEST_TASK;
    private static List<Task> TEST_TASKS;

    @Mock
    private TaskDao taskDao;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeAll
    public static void setupTasks() {
        TEST_TASK = new Task();
        TEST_TASK.setId(TEST_TASK_ID);
        TEST_TASKS = new ArrayList<>();
        TEST_TASKS.add(TEST_TASK);
    }

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldReturnTasks() {
        when(taskDao.findAll()).thenReturn(TEST_TASKS);
        List<Task> tasks = taskService.getTasks();
        assertNotNull(tasks);
        assertEquals(TEST_TASKS.size(), tasks.size());
        verify(taskDao, times(ONCE)).findAll();
    }

    @Test
    public void tasksIsEmpty_shouldThrowCustomEntityNotFoundException() {
        when(taskDao.findAll())
                .thenReturn(Collections.emptyList())
                .thenThrow(CustomEntityNotFoundException.class);

        assertThrows(CustomEntityNotFoundException.class, () -> taskService.getTasks());
    }

    @Test
    public void tasksIsNull_shouldThrowCustomEntityNotFoundException() {
        when(taskDao.findAll())
                .thenReturn(null)
                .thenThrow(CustomEntityNotFoundException.class);

        assertThrows(CustomEntityNotFoundException.class, () -> taskService.getTasks());
    }

    @Test
    public void shouldReturnTask() {
        when(taskDao.findById(TEST_TASK_ID)).thenReturn(Optional.of(TEST_TASK));
        Optional<Task> task = taskService.getTask(TEST_TASK_ID);
        assertNotNull(task);
        verify(taskDao, times(ONCE)).findById(TEST_TASK_ID);
    }

    @Test
    public void taskIsEmpty_shouldThrowCustomEntityNotFoundException_forGetTask() {
        when(taskDao.findById(TEST_TASK_ID))
                .thenReturn(Optional.empty())
                .thenThrow(CustomEntityNotFoundException.class);

        assertThrows(CustomEntityNotFoundException.class,
                () -> taskService.getTask(TEST_TASK_ID));
    }

    @Test
    public void shouldSaveTask() {
        doNothing().when(taskDao).save(TEST_TASK);
        taskService.saveTask(TEST_TASK);
        verify(taskDao, times(ONCE)).save(TEST_TASK);
    }

    @Test
    public void shouldDeleteTask() {
        shouldReturnTask();
        doNothing().when(taskDao).deleteById(TEST_TASK_ID);
        taskService.deleteTask(TEST_TASK_ID);
        verify(taskDao, times(ONCE)).deleteById(TEST_TASK_ID);
    }

    @Test
    public void taskIsEmpty_shouldThrowCustomEntityNotFoundException_forDeleteTask() {
        when(taskDao.findById(TEST_TASK_ID))
                .thenReturn(Optional.empty())
                .thenThrow(CustomEntityNotFoundException.class);

        assertThrows(CustomEntityNotFoundException.class,
                () -> taskService.deleteTask(TEST_TASK_ID));
    }
}