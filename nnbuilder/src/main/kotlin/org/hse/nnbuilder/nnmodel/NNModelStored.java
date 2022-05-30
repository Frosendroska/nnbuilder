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
@Table(name = "models")
public class NNModelStored {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mId;

    @Column(columnDefinition = "text")
    private String model;

    @OneToOne(cascade = CascadeType.ALL)
    TaskQueued task;

    // Id
    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    // Model
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Tasks
    public TaskQueued getTask() {
        return task;
    }

    public void setTask(TaskQueued tasks) {
        this.task = tasks;
    }
}
