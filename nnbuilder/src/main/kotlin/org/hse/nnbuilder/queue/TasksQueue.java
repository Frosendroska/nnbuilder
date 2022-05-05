package org.hse.nnbuilder.queue;

import java.sql.Timestamp;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hse.nnbuilder.services.Tasksqueue.TaskName;
import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;

@Entity
@Table(name = "tasksqueue")
public final class TasksQueue {

    @Id
    private Long id = new Random().nextLong();

    @Column
    TaskName taskName;

    @Column
    Long nnId;

    @Column
    Long dataId;

    @Column
    Timestamp startTime;

    @Column
    TaskStatus taskStatus;

    TasksQueue() {
    }

    private TasksQueue(TaskName taskName, Long nnId, Long dataId, Timestamp startTime, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.nnId = nnId;
        this.dataId = dataId;
        this.startTime = startTime;
        this.taskStatus = taskStatus;
    }

    TasksQueue(TaskName taskName, Long nnId, Long dataId) {
        this(taskName, nnId, dataId, new Timestamp(System.currentTimeMillis()), TaskStatus.HaveNotStarted);
    }

    // ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // StartTime
    public void setStartTime(Timestamp startTime) {this.startTime = startTime;}

    public Timestamp getStartTime() {return startTime;}

    // TaskName
    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    public TaskName getTaskName() {
        return taskName;
    }

    // Task Status
    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskStatus getTaskStatus(TaskStatus taskStatus) {
        return taskStatus;
    }

    // Neural Network Id
    public void setNnId(Long nnId) {
        this.nnId = nnId;
    }

    public Long getNnId(Long nnId) {
        return nnId;
    }

    // Data Set Id
    public void setDataSetId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId(Long dataId) {
        return dataId;
    }
}
