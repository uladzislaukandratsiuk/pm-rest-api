package com.demo.rest.api.dao;

import com.demo.rest.api.entity.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProjectDaoImpl implements ProjectDao {

    private final SessionFactory sessionFactory;

    public ProjectDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Project> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Project> query = session.createQuery("from Project", Project.class);
        return query.getResultList();
    }

    @Override
    public Optional<Project> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Project project = session.get(Project.class, id);
        return Optional.ofNullable(project);
    }

    @Override
    public void save(Project project) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(project);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Project where id=:projectId");
        query.setParameter("projectId", id);
        query.executeUpdate();
    }
}
