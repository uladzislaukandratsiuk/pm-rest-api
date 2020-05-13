package com.demo.rest.api.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "status")
    private String status;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "planned_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date plannedEndDate;

    @Column(name = "actual_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualEndDate;

    @Column(name = "comment")
    private String comment;

    public Activity() {
    }

    public Activity(String activityName, String status, Date startDate,
                    Date plannedEndDate, Date actualEndDate, String comment) {
        this.activityName = activityName;
        this.status = status;
        this.startDate = startDate;
        this.plannedEndDate = plannedEndDate;
        this.actualEndDate = actualEndDate;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(Date plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", plannedEndDate=" + plannedEndDate +
                ", actualEndDate=" + actualEndDate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
