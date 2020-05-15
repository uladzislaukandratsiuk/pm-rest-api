package com.demo.rest.api.service;

import com.demo.rest.api.dao.ActivityDao;
import com.demo.rest.api.entity.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

    @Disabled("method not implemented")
    @Test
    public void shouldReturnActivities() {
        when(activityService.getActivities()).thenReturn(ACTIVITIES);
        verify(activityDao, times(ONCE)).findAll();
    }

    @Disabled("method not implemented")
    @Test
    public void shouldReturnActivity() {
        when(activityService.getActivity(ACTIVITY_ID)).thenReturn(Optional.of((ACTIVITY)));
        verify(activityDao, times(ONCE)).findById(ACTIVITY_ID);
    }

    @Disabled("method not implemented")
    @Test
    public void shouldSaveActivity() {
        doNothing().when(spy(activityService)).saveActivity(ACTIVITY);
        verify(activityDao, times(ONCE)).save(ACTIVITY);
    }

    @Disabled("method not implemented")
    @Test
    public void shouldDeleteActivity() {
        doNothing().when(spy(activityService)).deleteActivity(ACTIVITY_ID);
        verify(activityDao, times(ONCE)).deleteById(ACTIVITY_ID);
    }
}