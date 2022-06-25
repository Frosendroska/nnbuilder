package org.hse.nnbuilder.queue;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hse.nnbuilder.dataset.DatasetStored;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.nnmodel.NNTrainedModelStored;
import org.hse.nnbuilder.prediction.Prediction;
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
    @JoinColumn(name = "datasetId")
    private DatasetStored datasetStored;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelId")
    private NNTrainedModelStored nnModelStored;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predictionId")
    private Prediction predictions;

    @Column
    private Long epochAmount;

    @Column
    private OffsetDateTime createTaskTime;

    @Column
    private OffsetDateTime startTaskTime;

    @Column
    private OffsetDateTime finishTaskTime;

    @Column
    private TaskStatus taskStatus;

    public TaskQueued() {}

    private TaskQueued(
            TaskType taskType,
            NeuralNetworkStored neuralNetworkStored,
            DatasetStored datasetStored,
            TaskStatus taskStatus,
            Long epochAmount) {

        OffsetDateTime timestamp = OffsetDateTime.now();
        this.taskType = taskType;
        this.neuralNetworkStored = neuralNetworkStored;
        this.datasetStored = datasetStored;
        this.createTaskTime = timestamp;
        this.startTaskTime = null; // Will set is after status will be "Processing"
        this.finishTaskTime = null; // Will set is after status will be "Finished"
        this.taskStatus = taskStatus;
        this.epochAmount = epochAmount;
    }

    public TaskQueued(
            TaskType taskType, NeuralNetworkStored neuralNetworkStored, DatasetStored datasetStored, Long epochAmount) {
        this(taskType, neuralNetworkStored, datasetStored, TaskStatus.HaveNotStarted, epochAmount);
    }

    // ID
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    // StartTime
    public void setCreateTaskTime(OffsetDateTime createTaskTime) {
        this.createTaskTime = createTaskTime;
    }

    public OffsetDateTime getCreateTaskTime() {
        return createTaskTime;
    }

    // StartTime
    public void setStartTaskTime(OffsetDateTime startTaskTime) {
        this.startTaskTime = startTaskTime;
    }

    public OffsetDateTime getStartTaskTime() {
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

    // NN Model Stored
    public NNTrainedModelStored getNnModelStored() {
        return nnModelStored;
    }

    public void setNnModelStored(NNTrainedModelStored nnModelStored) {
        this.nnModelStored = nnModelStored;
    }

    // Answers
    public Prediction getPredictions() {
        return predictions;
    }

    public void setPredictions(Prediction predictions) {
        this.predictions = predictions;
    }

    // Finish Task Time
    public OffsetDateTime getFinishTaskTime() {
        return finishTaskTime;
    }

    public void setFinishTaskTime(OffsetDateTime finishTaskTime) {
        this.finishTaskTime = finishTaskTime;
    }
}
