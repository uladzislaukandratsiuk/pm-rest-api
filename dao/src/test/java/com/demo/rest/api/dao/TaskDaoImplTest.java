package com.demo.rest.api.dao;

import com.demo.rest.api.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TaskDaoImplTest {

    private static final int ONCE = 1;
    private static final long TASK_ID = 1L;
    private static final Task TASK = new Task();
    private static final List<Task> TASKS = new ArrayList<>();

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Task> taskQuery;

    @Mock
    private Query query;

    @InjectMocks
    private TaskDaoImpl taskDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void shouldReturnListOfTasks() {
        when(session.createQuery("from Task", Task.class)).thenReturn(taskQuery);
        when(taskQuery.getResultList()).thenReturn(TASKS);
        when(taskDao.findAll()).thenReturn(TASKS);
        assertEquals(taskDao.findAll(), TASKS);
    }

    @Test
    void shouldReturnTaskById() {
        when(session.get(Task.class, TASK_ID)).thenReturn(TASK);
        when(taskDao.findById(TASK_ID)).thenReturn(Optional.of(TASK));
    }

    @Test
    void shouldSaveTask() {
        TaskDaoImpl spyDao = spy(taskDao);
        doNothing().when(session).saveOrUpdate(TASK);
        spyDao.save(TASK);
        verify(spyDao, times(ONCE)).save(TASK);
    }

    @Test
    void shouldRemoveTaskById() {
        TaskDaoImpl spyDao = spy(taskDao);
        when(session.createQuery("delete from Task where id=:taskId")).thenReturn(query);
        when(query.setParameter("taskId", TASK_ID)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(ONCE);
        spyDao.deleteById(TASK_ID);
        verify(spyDao, times(ONCE)).deleteById(TASK_ID);
    }
}