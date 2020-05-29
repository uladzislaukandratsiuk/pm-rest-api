package com.demo.rest.api.controller;

import com.demo.rest.api.entity.Project;
import com.demo.rest.api.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectRestController {

    private final ProjectService projectService;

    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/projects/{projectId}")
    public Optional<Project> getProject(@PathVariable Long projectId) {
        return projectService.getProject(projectId);
    }

    @PostMapping("/projects")
    public Project addProject(@RequestBody Project project) {
        projectService.saveProject(project);
        return project;
    }

    @PutMapping("/projects")
    public Project updateProject(@RequestBody Project project) {
        projectService.saveProject(project);
        return project;
    }

    @DeleteMapping("/projects/{projectId}")
    public String deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return "Deleted Project with id=" + projectId;
    }
}
