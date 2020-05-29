package com.demo.rest.api.controller;

import com.demo.rest.api.entity.Project;
import com.demo.rest.api.service.ProjectService;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
class ProjectRestControllerTest {

    private static final int ONCE = 1;
    private static final long PROJECT_ID = 1L;
    private static Project PROJECT;
    private static Project UPDATED_PROJECT;
    private static final List<Project> PROJECTS = new ArrayList<>();

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectRestController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeAll
    public static void initObjects() {
        initProjectObject();
        initUpdatedProjectObject();
        PROJECTS.add(PROJECT);
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
    void shouldGetProjects() throws Exception {
        when(projectService.getProjects()).thenReturn(PROJECTS);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(projectService, Mockito.times(ONCE)).getProjects();
        verifyNoMoreInteractions(projectService);
    }

    @Test
    void shouldGetProject() throws Exception {
        when(projectService.getProject(PROJECT_ID)).thenReturn(Optional.of(PROJECT));

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + PROJECT_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(projectService, times(ONCE)).getProject(PROJECT_ID);
        verifyNoMoreInteractions(projectService);
    }

    @Test
    void shouldAddProject() throws Exception {
        doNothing().when(projectService).saveProject(PROJECT);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/projects")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(PROJECT))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(projectService, times(ONCE)).saveProject(PROJECT);
        verifyNoMoreInteractions(projectService);
    }

    @Test
    void shouldUpdateProject() throws Exception {
        doNothing().when(projectService).saveProject(UPDATED_PROJECT);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/projects")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(UPDATED_PROJECT))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(projectService, times(ONCE)).saveProject(UPDATED_PROJECT);
        verifyNoMoreInteractions(projectService);
    }

    @Test
    void shouldDeleteProject() throws Exception {
        doNothing().when(projectService).deleteProject(PROJECT_ID);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/projects/" + PROJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(PROJECT))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        verify(projectService, times(ONCE)).deleteProject(PROJECT_ID);
        verifyNoMoreInteractions(projectService);
    }

    private static void initProjectObject() {
        PROJECT = new Project("test project", "Low", Date.valueOf("2020-05-18"));
        PROJECT.setId(PROJECT_ID);
    }

    private static void initUpdatedProjectObject() {
        UPDATED_PROJECT = PROJECT;
        UPDATED_PROJECT.setProjectName("updated project");
        UPDATED_PROJECT.setStatus("High");
        UPDATED_PROJECT.setPlannedEndDate(Date.valueOf("2020-06-30"));
    }
}