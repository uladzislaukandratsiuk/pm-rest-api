package com.demo.rest.api.service;

import com.demo.rest.api.entity.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {

    List<Activity> getActivities();

    Optional<Activity> getActivity(Long id);

    void saveActivity(Activity activity);

    void deleteActivity(Long id);
}
