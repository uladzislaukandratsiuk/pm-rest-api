package com.demo.rest.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "status")
    private String status;

    @Column(name = "start_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING,
            pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+3")
    private Date startDate;

    @Column(name = "planned_end_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING,
            pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+3")
    private Date plannedEndDate;

    @Column(name = "actual_end_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING,
            pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+3")
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;

        Activity activity = (Activity) o;

        if (!Objects.equals(id, activity.id)) return false;
        return Objects.equals(activityName, activity.activityName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (activityName != null ? activityName.hashCode() : 0);
        return result;
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
