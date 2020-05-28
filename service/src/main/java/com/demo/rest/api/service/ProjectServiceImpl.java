package com.demo.rest.api.service;

import com.demo.rest.api.dao.ProjectDao;
import com.demo.rest.api.entity.Project;
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
        return projectDao.findAll();
    }

    @Override
    @Transactional
    public Optional<Project> getProject(Long id) {
        return projectDao.findById(id);
    }

    @Override
    @Transactional
    public void saveProject(Project project) {
        projectDao.save(project);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        projectDao.deleteById(id);
    }
}
