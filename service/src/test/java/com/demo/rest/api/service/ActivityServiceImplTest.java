package com.demo.rest.api.service;

import com.demo.rest.api.dao.ActivityDao;
import com.demo.rest.api.entity.Activity;
import com.demo.rest.api.exception.ActivityNotFoundException;
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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ActivityServiceImplTest {

    private static final int ONCE = 1;
    private static final long TEST_ACTIVITY_ID = 1L;
    private static Activity TEST_ACTIVITY;
    private static List<Activity> TEST_ACTIVITIES;

    @Mock
    private ActivityDao activityDao;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @BeforeAll
    public static void setupActivities() {
        TEST_ACTIVITY = new Activity();
        TEST_ACTIVITY.setId(TEST_ACTIVITY_ID);
        TEST_ACTIVITIES = new ArrayList<>();
        TEST_ACTIVITIES.add(TEST_ACTIVITY);
    }

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldReturnActivities() {
        when(activityDao.findAll()).thenReturn(TEST_ACTIVITIES);
        List<Activity> activities = activityService.getActivities();
        assertNotNull(activities);
        assertEquals(TEST_ACTIVITIES.size(), activities.size());
        verify(activityDao, times(ONCE)).findAll();
    }

    @Test
    public void activitiesIsEmpty_shouldThrowActivityNotFoundException() {
        when(activityDao.findAll())
                .thenReturn(Collections.emptyList())
                .thenThrow(ActivityNotFoundException.class);

        assertThrows(ActivityNotFoundException.class, () -> activityService.getActivities());
    }

    @Test
    public void activitiesIsNull_shouldThrowActivityNotFoundException() {
        when(activityDao.findAll())
                .thenReturn(null)
                .thenThrow(ActivityNotFoundException.class);

        assertThrows(ActivityNotFoundException.class, () -> activityService.getActivities());
    }

    @Test
    public void shouldReturnActivity() {
        when(activityDao.findById(TEST_ACTIVITY_ID)).thenReturn(Optional.of(TEST_ACTIVITY));
        activityService.getActivity(TEST_ACTIVITY_ID);
        verify(activityDao, times(ONCE)).findById(TEST_ACTIVITY_ID);
    }

    @Test
    public void shouldSaveActivity() {
        doNothing().when(activityDao).save(TEST_ACTIVITY);
        activityService.saveActivity(TEST_ACTIVITY);
        verify(activityDao, times(ONCE)).save(TEST_ACTIVITY);
    }

    @Test
    public void shouldDeleteActivity() {
        doNothing().when(activityDao).deleteById(TEST_ACTIVITY_ID);
        activityService.deleteActivity(TEST_ACTIVITY_ID);
        verify(activityDao, times(ONCE)).deleteById(TEST_ACTIVITY_ID);
    }
}