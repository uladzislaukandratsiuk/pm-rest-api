package com.demo.rest.api.dao;

import com.demo.rest.api.entity.Activity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ActivityDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private ActivityDaoImpl activityDao;

    private Session session;

    private Query<Activity> activityQuery;

    @BeforeEach
    public void setup() {
        initMocks(this);
        session = mock(Session.class);
        activityQuery = mock(Query.class);
    }

    @Test
    void whenInvokeFindAll_shouldReturnListOfActivities() {
        List<Activity> activities = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("from Activity", Activity.class)).thenReturn(activityQuery);
        when(activityQuery.getResultList()).thenReturn(activities);
        when(activityDao.findAll()).thenReturn(activities);
    }

    @Test
    void whenInvokeFindByID_shouldReturnActivity() {
        Activity activity = new Activity();
        Long id = 1L;
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.get(Activity.class, id)).thenReturn(activity);
        when(activityDao.findById(id)).thenReturn(Optional.of(activity));
    }

    @Test
    void whenInvokeSave_shouldDoNothing() {
        Activity activity = new Activity();
        ActivityDaoImpl spyDao = spy(activityDao);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        Assertions.assertNotNull(activity);
        doNothing().when(session).saveOrUpdate(activity);
        spyDao.save(activity);
        verify(spyDao, times(1)).save(activity);
    }

    @Test
    void whenDeleteById_shouldRemoveActivity() {
        Long id = 1L;
        ActivityDaoImpl spyDao = spy(activityDao);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("delete from Activity where id=:activityId", Activity.class))
                .thenReturn(activityQuery);
        when(activityQuery.setParameter("activityId", id)).thenReturn(activityQuery);
        when(activityQuery.executeUpdate()).thenReturn(1);
        spyDao.deleteById(id);
        verify(spyDao, times(1)).deleteById(id);
    }
}