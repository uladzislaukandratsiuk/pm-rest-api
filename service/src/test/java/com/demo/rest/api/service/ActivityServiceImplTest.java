package com.demo.rest.api.service;

import com.demo.rest.api.dao.ActivityDao;
import com.demo.rest.api.entity.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ActivityServiceImplTest {

    private static final int ONCE = 1;
    private static final long ACTIVITY_ID = 1L;
    private static final Activity ACTIVITY = new Activity();
    private static final List<Activity> ACTIVITIES = new ArrayList<>();

    @Mock
    private ActivityDao activityDao;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldReturnActivities() {
        when(activityDao.findAll()).thenReturn(ACTIVITIES);
        activityService.getActivities();
        verify(activityDao, times(ONCE)).findAll();
    }

    @Test
    public void shouldReturnActivity() {
        when(activityDao.findById(ACTIVITY_ID)).thenReturn(Optional.of((ACTIVITY)));
        activityService.getActivity(ACTIVITY_ID);
        verify(activityDao, times(ONCE)).findById(ACTIVITY_ID);
    }

    @Test
    public void shouldSaveActivity() {
        doNothing().when(activityDao).save(ACTIVITY);
        activityService.saveActivity(ACTIVITY);
        verify(activityDao, times(ONCE)).save(ACTIVITY);
    }

    @Test
    public void shouldDeleteActivity() {
        doNothing().when(activityDao).deleteById(ACTIVITY_ID);
        activityService.deleteActivity(ACTIVITY_ID);
        verify(activityDao, times(ONCE)).deleteById(ACTIVITY_ID);
    }
}