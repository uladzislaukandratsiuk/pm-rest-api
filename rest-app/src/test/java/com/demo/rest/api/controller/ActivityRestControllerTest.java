package com.demo.rest.api.controller;

import com.demo.rest.api.entity.Activity;
import com.demo.rest.api.service.ActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
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

import java.sql.Date;
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
    private static final Activity UPDATED_ACTIVITY = new Activity();
    private static final List<Activity> ACTIVITIES = new ArrayList<>();

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityRestController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeAll
    public static void initObjects() {
        initActivityObject();
        initUpdatedActivityObject();
        ACTIVITIES.add(ACTIVITY);
    }

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
    void shouldGetActivity() throws Exception {
        when(activityService.getActivity(ACTIVITY_ID)).thenReturn(Optional.of(ACTIVITY));

        mockMvc.perform(MockMvcRequestBuilders.get("/activities/" + ACTIVITY_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(activityService, times(ONCE)).getActivity(ACTIVITY_ID);
        verifyNoMoreInteractions(activityService);
    }

    @Test
    void shouldAddActivity() throws Exception {
        doNothing().when(activityService).saveActivity(ACTIVITY);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/activities")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(ACTIVITY))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(activityService, times(ONCE)).saveActivity(ACTIVITY);
        verifyNoMoreInteractions(activityService);
    }

    @Test
    void shouldUpdateActivity() throws Exception {
        doNothing().when(activityService).saveActivity(UPDATED_ACTIVITY);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/activities")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(UPDATED_ACTIVITY))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(activityService, times(ONCE)).saveActivity(UPDATED_ACTIVITY);
        verifyNoMoreInteractions(activityService);
    }

    @Disabled
    @Test
    void deleteActivity() {
    }

    private static void initActivityObject() {
        ACTIVITY.setId(ACTIVITY_ID);
        ACTIVITY.setActivityName("test");
        ACTIVITY.setStatus("in progress");
        ACTIVITY.setStartDate(Date.valueOf("2020-05-17"));
        ACTIVITY.setPlannedEndDate(Date.valueOf("2020-05-17"));
        ACTIVITY.setActualEndDate(Date.valueOf("2020-05-18"));
        ACTIVITY.setComment("MockMvc test");
    }

    private static void initUpdatedActivityObject() {
        UPDATED_ACTIVITY.setId(ACTIVITY_ID);
        UPDATED_ACTIVITY.setActivityName("test");
        UPDATED_ACTIVITY.setStatus("completed");
        UPDATED_ACTIVITY.setStartDate(Date.valueOf("2020-05-17"));
        UPDATED_ACTIVITY.setPlannedEndDate(Date.valueOf("2020-05-17"));
        UPDATED_ACTIVITY.setActualEndDate(Date.valueOf("2020-05-18"));
        UPDATED_ACTIVITY.setComment("MockMvc test");
    }
}