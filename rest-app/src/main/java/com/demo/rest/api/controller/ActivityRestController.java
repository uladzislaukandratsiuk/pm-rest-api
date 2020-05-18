package com.demo.rest.api.controller;

import com.demo.rest.api.entity.Activity;
import com.demo.rest.api.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ActivityRestController {

    private final ActivityService activityService;

    public ActivityRestController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activities")
    public List<Activity> getActivities() {
        return activityService.getActivities();
    }

    @GetMapping("/activities/{activityId}")
    public Optional<Activity> getActivity(@PathVariable Long activityId) {
        return activityService.getActivity(activityId);
    }

    @PostMapping("/activities")
    public Activity addActivity(@RequestBody Activity activity) {
        activityService.saveActivity(activity);
        return activity;
    }

    @PutMapping("/activities")
    public Activity updateActivity(@RequestBody Activity activity) {
        activityService.saveActivity(activity);
        return activity;
    }

    @DeleteMapping("/activities/{activityId}")
    public String deleteActivity(@PathVariable Long activityId) {
        activityService.deleteActivity(activityId);
        return "Deleted Activity with id=" + activityId;
    }
}
