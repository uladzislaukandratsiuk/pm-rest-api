package com.demo.rest.api.dao;

import com.demo.rest.api.entity.Activity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActivityDaoImpl implements ActivityDao {

    private final SessionFactory sessionFactory;

    public ActivityDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Activity> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Activity> query = session.createQuery("from Activity", Activity.class);
        return query.getResultList();
    }

    @Override
    public Optional<Activity> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Activity activity = session.get(Activity.class, id);
        return Optional.ofNullable(activity);
    }

    @Override
    public void save(Activity activity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(activity);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Activity where id=:activityId");
        query.setParameter("activityId", id);
        query.executeUpdate();
    }
}
