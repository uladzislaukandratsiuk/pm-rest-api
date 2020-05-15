package com.demo.rest.api.service;

import com.demo.rest.api.dao.ActivityDao;
import com.demo.rest.api.entity.Activity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityDao activityDao;

    public ActivityServiceImpl(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    @Override
    @Transactional
    public List<Activity> getActivities() {
        return activityDao.findAll();
    }

    @Override
    @Transactional
    public Optional<Activity> getActivity(Long id) {
        return activityDao.findById(id);
    }

    @Override
    @Transactional
    public void saveActivity(Activity activity) {
        activityDao.save(activity);
    }

    @Override
    @Transactional
    public void deleteActivity(Long id) {
        activityDao.deleteById(id);
    }
}
