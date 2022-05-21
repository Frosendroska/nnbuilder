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
import org.hse.nnbuilder.dataset.DatasetStored;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;
import org.hse.nnbuilder.services.Tasksqueue.TaskType;

@Entity
@Table(name = "tasksqueue")
public final class TaskQueued {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    @Column
    private TaskType taskType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nnId")
    private NeuralNetworkStored neuralNetworkStored;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dsId")
    private DatasetStored datasetStored;
    @Column
    private Long epochAmount;
    @Column
    private Timestamp addTaskTime;
    @Column
    private Timestamp startTaskTime;
    @Column
    private TaskStatus taskStatus;

    TaskQueued() {}

    private TaskQueued(
            TaskType taskType, NeuralNetworkStored neuralNetworkStored,
            DatasetStored datasetStored, TaskStatus taskStatus
    ) {
        Instant now = Instant.now();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(now.getEpochSecond()).setNanos(now.getNano()).build();
        this.taskType = taskType;
        this.neuralNetworkStored = neuralNetworkStored;
        this.datasetStored = datasetStored;
        this.addTaskTime = timestamp;
        this.startTaskTime = null; // Will set is after status will be "Processing"
        this.taskStatus = taskStatus;
    }

    public TaskQueued(TaskType taskName, NeuralNetworkStored neuralNetworkStored, DatasetStored datasetStored) {
        this(taskName, neuralNetworkStored, datasetStored, TaskStatus.HaveNotStarted);
    }

    // ID
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    // StartTime
    public void setAddTaskTime(Timestamp addTaskTime) {
        this.addTaskTime = addTaskTime;
    }

    public Timestamp getAddTaskTime() {
        return addTaskTime;
    }

    // StartTime
    public void setStartTaskTime(Timestamp startTaskTime) {
        this.startTaskTime = startTaskTime;
    }

    public Timestamp getStartTaskTime() {
        return startTaskTime;
    }

    // TaskName
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskType getTaskType() {
        return taskType;
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
    public void setDatasetStored(DatasetStored datasetStored) {
        this.datasetStored = datasetStored;
    }

    public DatasetStored getDatasetStored() {
        return datasetStored;
    }

    // Epoch amount
    public Long getEpochAmount() {
        return epochAmount;
    }

    public void setEpochAmount(Long epochAmount) {
        this.epochAmount = epochAmount;
    }
}
