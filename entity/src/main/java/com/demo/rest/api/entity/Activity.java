package com.demo.rest.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(name = "last_update_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING,
            pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+3")
    private Date lastUpdateDate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task task;

    public Activity() {
    }

    public Activity(String activityName, String status, Date plannedEndDate) {
        this.activityName = activityName;
        this.status = status;
        this.plannedEndDate = plannedEndDate;
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

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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
                ", lastUpdateDate=" + lastUpdateDate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
