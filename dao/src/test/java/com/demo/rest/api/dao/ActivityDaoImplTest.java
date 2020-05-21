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

    private static final int ONCE = 1;
    private static final long ACTIVITY_ID = 1L;
    private static final Activity ACTIVITY = new Activity();
    private static final List<Activity> ACTIVITIES = new ArrayList<>();

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Activity> activityQuery;

    @Mock
    private Query query;

    @InjectMocks
    private ActivityDaoImpl activityDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void shouldReturnListOfActivities() {
        when(session.createQuery("from Activity", Activity.class)).thenReturn(activityQuery);
        when(activityQuery.getResultList()).thenReturn(ACTIVITIES);
        when(activityDao.findAll()).thenReturn(ACTIVITIES);
        assertEquals(activityDao.findAll(), ACTIVITIES);
    }

    @Test
    void shouldReturnActivityById() {
        when(session.get(Activity.class, ACTIVITY_ID)).thenReturn(ACTIVITY);
        when(activityDao.findById(ACTIVITY_ID)).thenReturn(Optional.of(ACTIVITY));
    }

    @Test
    void shouldSaveActivity() {
        ActivityDaoImpl spyDao = spy(activityDao);
        doNothing().when(session).saveOrUpdate(ACTIVITY);
        spyDao.save(ACTIVITY);
        verify(spyDao, times(ONCE)).save(ACTIVITY);
    }

    @Test
    void shouldRemoveActivityById() {
        ActivityDaoImpl spyDao = spy(activityDao);
        when(session.createQuery("delete from Activity where id=:activityId")).thenReturn(query);
        when(query.setParameter("activityId", ACTIVITY_ID)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(ONCE);
        spyDao.deleteById(ACTIVITY_ID);
        verify(spyDao, times(ONCE)).deleteById(ACTIVITY_ID);
    }
}