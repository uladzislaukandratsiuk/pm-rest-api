package com.demo.rest.api.controller;

import com.demo.rest.api.entity.Activity;
import com.demo.rest.api.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
class ActivityRestControllerTest {

    private static final int ONCE = 1;
    private static final long ACTIVITY_ID = 1L;
    private static final Activity ACTIVITY = new Activity();
    private static final List<Activity> ACTIVITIES = new ArrayList<>();

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityRestController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void shouldGetActivities() throws Exception {
        when(activityService.getActivities()).thenReturn(ACTIVITIES);

        mockMvc.perform(MockMvcRequestBuilders.get("/activities")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(activityService, Mockito.times(ONCE)).getActivities();
        verifyNoMoreInteractions(activityService);
    }

    @Test
    void getActivity() throws Exception {
        when(activityService.getActivity(ACTIVITY_ID)).thenReturn(Optional.of(ACTIVITY));

        mockMvc.perform(MockMvcRequestBuilders.get("/activities/" + ACTIVITY_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(activityService, times(ONCE)).getActivity(ACTIVITY_ID);
        verifyNoMoreInteractions(activityService);
    }

    @Disabled
    @Test
    void addActivity() {
    }

    @Disabled
    @Test
    void updateActivity() {
    }

    @Disabled
    @Test
    void deleteActivity() {
    }
}