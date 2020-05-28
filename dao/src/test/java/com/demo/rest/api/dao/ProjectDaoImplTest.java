package com.demo.rest.api.dao;

import com.demo.rest.api.entity.Project;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ProjectDaoImplTest {

    private static final int ONCE = 1;
    private static final long PROJECT_ID = 1L;
    private static final Project PROJECT = new Project();
    private static final List<Project> PROJECTS = new ArrayList<>();

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Project> projectQuery;

    @Mock
    private Query query;

    @InjectMocks
    private ProjectDaoImpl projectDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void shouldReturnListOfProjects() {
        when(session.createQuery("from Project", Project.class)).thenReturn(projectQuery);
        when(projectQuery.getResultList()).thenReturn(PROJECTS);
        when(projectDao.findAll()).thenReturn(PROJECTS);
        assertEquals(projectDao.findAll(), PROJECTS);
    }

    @Test
    void shouldReturnProjectById() {
        when(session.get(Project.class, PROJECT_ID)).thenReturn(PROJECT);
        when(projectDao.findById(PROJECT_ID)).thenReturn(Optional.of(PROJECT));
    }

    @Test
    void shouldSaveProject() {
        ProjectDaoImpl spyDao = spy(projectDao);
        doNothing().when(session).saveOrUpdate(PROJECT);
        spyDao.save(PROJECT);
        verify(spyDao, times(ONCE)).save(PROJECT);
    }

    @Test
    void shouldRemoveProjectById() {
        ProjectDaoImpl spyDao = spy(projectDao);
        when(session.createQuery("delete from Project where id=:projectId")).thenReturn(query);
        when(query.setParameter("projectId", PROJECT_ID)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(ONCE);
        spyDao.deleteById(PROJECT_ID);
        verify(spyDao, times(ONCE)).deleteById(PROJECT_ID);
    }
}