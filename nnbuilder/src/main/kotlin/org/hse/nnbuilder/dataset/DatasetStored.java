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
    private String targetColumnName;

    @Column
    private byte[] content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<TaskQueued> tasks = new ArrayList<>();

    public DatasetStored() {}

    public DatasetStored(byte[] content, String targetColumnName) {
        this.targetColumnName = targetColumnName;
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

    // Target Column Name
    public String getTargetColumnName() {
        return targetColumnName;
    }

    public void setTargetColumnName(String targetColumnName) {
        this.targetColumnName = targetColumnName;
    }
}
