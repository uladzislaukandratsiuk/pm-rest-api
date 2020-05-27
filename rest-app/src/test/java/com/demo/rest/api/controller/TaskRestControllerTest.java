package com.demo.rest.api.controller;

import com.demo.rest.api.entity.Task;
import com.demo.rest.api.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
class TaskRestControllerTest {

    private static final int ONCE = 1;
    private static final long TASK_ID = 1L;
    private static Task TASK;
    private static Task UPDATED_TASK;
    private static final List<Task> TASKS = new ArrayList<>();

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskRestController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeAll
    public static void initObjects() {
        initTaskObject();
        initUpdatedTaskObject();
        TASKS.add(TASK);
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
    void shouldGetTasks() throws Exception {
        when(taskService.getTasks()).thenReturn(TASKS);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(taskService, Mockito.times(ONCE)).getTasks();
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void shouldGetTask() throws Exception {
        when(taskService.getTask(TASK_ID)).thenReturn(Optional.of(TASK));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/" + TASK_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(taskService, times(ONCE)).getTask(TASK_ID);
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void shouldAddTask() throws Exception {
        doNothing().when(taskService).saveTask(TASK);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(TASK))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(taskService, times(ONCE)).saveTask(TASK);
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        doNothing().when(taskService).saveTask(UPDATED_TASK);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(UPDATED_TASK))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(taskService, times(ONCE)).saveTask(UPDATED_TASK);
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void shouldDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(TASK_ID);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/" + TASK_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(TASK))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(taskService, times(ONCE)).deleteTask(TASK_ID);
        verifyNoMoreInteractions(taskService);
    }

    private static void initTaskObject() {
        TASK = new Task("task test", "High", "In progress");
        TASK.setId(TASK_ID);
    }

    private static void initUpdatedTaskObject() {
        UPDATED_TASK = TASK;
        UPDATED_TASK.setTaskName("updated task test");
        UPDATED_TASK.setPriority("Low");
        UPDATED_TASK.setStatus("Completed");
    }
}