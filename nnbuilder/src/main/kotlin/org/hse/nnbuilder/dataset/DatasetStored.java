package org.hse.nnbuilder.dataset;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hse.nnbuilder.queue.TaskQueued;

@Entity
@Table(name = "datasets")
public class DatasetStored {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dsId;

    @Column
    private byte[] content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<TaskQueued> tasks = new ArrayList<>();

    public DatasetStored() {}

    public DatasetStored(byte[] content) {
        this.content = content;
    }

    // Dataset Id
    public Long getDsId() {
        return dsId;
    }

    void setDsId(Long dsId) {
        this.dsId = dsId;
    }

    // Data
    public byte[] getContent() {
        return content;
    }

    void setContent(byte[] content) {
        this.content = content;
    }

    // Tasks
    public List<TaskQueued> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskQueued> tasks) {
        this.tasks = tasks;
    }

    //
    public void addTask(TaskQueued task) {
        tasks.add(task);
        task.setDatasetStored(this);
    }

    public void removeTask(TaskQueued task) {
        tasks.remove(task);
        task.setDatasetStored(null);
    }
}
