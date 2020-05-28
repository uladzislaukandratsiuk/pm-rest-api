package com.demo.rest.api.service;

import com.demo.rest.api.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> getProjects();

    Optional<Project> getProject(Long id);

    void saveProject(Project project);

    void deleteProject(Long id);
}
