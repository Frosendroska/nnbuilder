package org.hse.nnbuilder.prediction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hse.nnbuilder.queue.TaskQueued;

@Entity
@Table(name = "predictions")
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long predictionId;

    @Column(columnDefinition = "text")
    private byte[] predictions;

    @OneToOne(cascade = CascadeType.ALL)
    TaskQueued task;

    // Id
    public Long getPredictionId() {
        return predictionId;
    }

    public void setPredictionId(Long predictionId) {
        this.predictionId = predictionId;
    }

    // Model
    public byte[] getPredictions() {
        return this.predictions;
    }

    public void setPredictions(byte[] predictions) {
        this.predictions = predictions;
    }

    // Tasks
    public TaskQueued getTask() {
        return task;
    }

    public void setTask(TaskQueued tasks) {
        this.task = tasks;
    }
}
