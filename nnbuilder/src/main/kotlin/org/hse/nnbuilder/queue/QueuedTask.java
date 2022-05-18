package org.hse.nnbuilder.queue;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;
import org.hse.nnbuilder.services.Tasksqueue.TaskType;

@Entity
@Table(name = "tasksqueue")
public final class QueuedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    @Column
    TaskType taskName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nnId")
    NeuralNetworkStored neuralNetworkStored;
    @Column
    Long dataId;
    @Column
    Timestamp startTime;
    @Column
    TaskStatus taskStatus;

    QueuedTask() {}

    private QueuedTask(TaskType taskName, NeuralNetworkStored neuralNetworkStored, Long dataId, TaskStatus taskStatus) {
        Instant now = Instant.now();
        Timestamp timestamp =
                Timestamp.newBuilder().setSeconds(now.getEpochSecond())
                        .setNanos(now.getNano()).build();
        this.taskName = taskName;
        this.neuralNetworkStored = neuralNetworkStored;
        this.dataId = dataId;
        this.startTime = timestamp;
        this.taskStatus = taskStatus;
    }

    public QueuedTask(TaskType taskName, NeuralNetworkStored neuralNetworkStored, Long dataId) {
        this(taskName, neuralNetworkStored, dataId, TaskStatus.HaveNotStarted);
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
    public void setNeuralNetworkStored(NeuralNetworkStored neuralNetworkStored) {
        this.neuralNetworkStored = neuralNetworkStored;
    }

    public NeuralNetworkStored getNeuralNetworkStored() {
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
