package com.demo.rest.api.dao;

import com.demo.rest.api.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDaoImpl implements TaskDao {

    private final SessionFactory sessionFactory;

    public TaskDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Task> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Task> query = session.createQuery("from Task", Task.class);
        return query.getResultList();
    }

    @Override
    public Optional<Task> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Task task = session.get(Task.class, id);
        return Optional.ofNullable(task);
    }

    @Override
    public void save(Task task) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(task);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Task where id=:taskId");
        query.setParameter("taskId", id);
        query.executeUpdate();
    }
}
