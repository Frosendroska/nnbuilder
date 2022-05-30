package org.hse.nnbuilder.answers;

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
@Table(name = "answers")
public class Predictions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

    @Column(columnDefinition = "text")
    private String predictions;

    @OneToOne(cascade = CascadeType.ALL)
    TaskQueued task;

    // Id
    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    // Model
    public String getPredictions() {
        return this.predictions;
    }

    public void setPredictions(String predictions) {
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
