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
    private Long taskId = new Random().nextLong();
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

    TasksQueue() {}

    private TasksQueue(TaskName taskName, Long nnId, Long dataId, Timestamp startTime, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.nnId = nnId;
        this.dataId = dataId;
        this.startTime = startTime;
        this.taskStatus = taskStatus;
    }

    public TasksQueue(TaskName taskName, Long nnId, Long dataId) {
        this(taskName, nnId, dataId, new Timestamp(System.currentTimeMillis()), TaskStatus.HaveNotStarted);
    }

    // ID
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    // StartTime
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

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

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    // Neural Network Id
    public void setNnId(Long nnId) {
        this.nnId = nnId;
    }

    public Long getNnId() {
        return nnId;
    }

    // Data Set Id
    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId() {
        return dataId;
    }
}
