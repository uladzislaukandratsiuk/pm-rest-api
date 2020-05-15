package com.demo.rest.api.dao;

import com.demo.rest.api.entity.Activity;
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

class ActivityDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Activity> activityQuery;

    @InjectMocks
    private ActivityDaoImpl activityDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void shouldReturnListOfActivities() {
        List<Activity> activities = new ArrayList<>();
        when(session.createQuery("from Activity", Activity.class)).thenReturn(activityQuery);
        when(activityQuery.getResultList()).thenReturn(activities);
        when(activityDao.findAll()).thenReturn(activities);
        assertEquals(activityDao.findAll(), activities);
    }

    @Test
    void shouldReturnActivityById() {
        Activity activity = new Activity();
        Long id = 1L;
        when(session.get(Activity.class, id)).thenReturn(activity);
        when(activityDao.findById(id)).thenReturn(Optional.of(activity));
    }

    @Test
    void shouldSaveActivity() {
        Activity activity = new Activity();
        ActivityDaoImpl spyDao = spy(activityDao);
        doNothing().when(session).saveOrUpdate(activity);
        spyDao.save(activity);
        verify(spyDao, times(1)).save(activity);
    }

    @Test
    void shouldRemoveActivityById() {
        Long id = 1L;
        ActivityDaoImpl spyDao = spy(activityDao);
        when(session.createQuery("delete from Activity where id=:activityId", Activity.class))
                .thenReturn(activityQuery);
        when(activityQuery.setParameter("activityId", id)).thenReturn(activityQuery);
        when(activityQuery.executeUpdate()).thenReturn(1);
        spyDao.deleteById(id);
        verify(spyDao, times(1)).deleteById(id);
    }
}