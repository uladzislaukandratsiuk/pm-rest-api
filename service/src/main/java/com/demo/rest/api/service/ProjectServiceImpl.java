package com.demo.rest.api.service;

import com.demo.rest.api.dao.ProjectDao;
import com.demo.rest.api.entity.Project;
import com.demo.rest.api.exception.CustomEntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;

    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    @Transactional
    public List<Project> getProjects() {
        List<Project> projects = projectDao.findAll();
        if (projects == null || projects.isEmpty())
            throw new CustomEntityNotFoundException("No Projects data found!");
        return projects;
    }

    @Override
    @Transactional
    public Optional<Project> getProject(Long id) {
        Optional<Project> project = projectDao.findById(id);
        if (project.isEmpty())
            throw new CustomEntityNotFoundException("Project with id=" + id + " not found!");
        return project;
    }

    @Override
    @Transactional
    public void saveProject(Project project) {
        projectDao.save(project);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        Optional<Project> project = projectDao.findById(id);
        if (project.isEmpty())
            throw new CustomEntityNotFoundException("Project with id=" + id + " not found!");
        projectDao.deleteById(id);
    }
}
