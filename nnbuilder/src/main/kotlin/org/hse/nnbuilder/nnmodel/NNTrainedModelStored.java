package org.hse.nnbuilder.nnmodel;

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
@Table(name = "nntrained_models")
public class NNTrainedModelStored {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nnTrainedModelId;

    @Column(columnDefinition = "text")
    private String nnTrainedModel;

    @OneToOne(cascade = CascadeType.ALL)
    TaskQueued task;

    // Id
    public Long getNnTrainedModelId() {
        return nnTrainedModelId;
    }

    public void setNnTrainedModelId(Long nnTrainedModelId) {
        this.nnTrainedModelId = nnTrainedModelId;
    }

    // Model
    public String getNnTrainedModel() {
        return this.nnTrainedModel;
    }

    public void setNnTrainedModel(String nnTrainedModel) {
        this.nnTrainedModel = nnTrainedModel;
    }

    // Tasks
    public TaskQueued getTask() {
        return task;
    }

    public void setTask(TaskQueued task) {
        this.task = task;
    }
}
