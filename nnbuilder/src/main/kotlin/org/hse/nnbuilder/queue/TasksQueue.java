package org.hse.nnbuilder.queue;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;
import org.hse.nnbuilder.services.Tasksqueue.TaskType;

@Entity
@Table(name = "tasksqueue")
public final class TasksQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    @Column
    TaskType taskName;
    // @Column
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nnId", referencedColumnName = "nnId")
    NeuralNetworkStored neuralNetworkStored;
    @Column
    Long dataId;
    @Column
    Timestamp startTime;
    @Column
    TaskStatus taskStatus;

    TasksQueue() {}

    private TasksQueue(
            TaskType taskName,
            NeuralNetworkStored neuralNetworkStored,
            Long dataId,
            Timestamp startTime,
            TaskStatus taskStatus) {
        this.taskName = taskName;
        this.neuralNetworkStored = neuralNetworkStored;
        this.dataId = dataId;
        this.startTime = startTime;
        this.taskStatus = taskStatus;
    }

    public TasksQueue(TaskType taskName, NeuralNetworkStored neuralNetworkStored, Long dataId) {
        this(
                taskName,
                neuralNetworkStored,
                dataId,
                new Timestamp(System.currentTimeMillis()),
                TaskStatus.HaveNotStarted);
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
    public void setTaskName(TaskType taskName) {
        this.taskName = taskName;
    }

    public TaskType getTaskName() {
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
        this.neuralNetworkStored = neuralNetworkStored;
    }

    public NeuralNetworkStored getNnId() {
        return neuralNetworkStored;
    }

    // Data Set Id
    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId() {
        return dataId;
    }
}
