package org.hse.nnbuilder.dataset;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hse.nnbuilder.queue.TaskQueued;

@Entity
@Table(name = "datasets")
public class DatasetStored {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long datasetId;

    @Column
    private byte[] data;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<TaskQueued> tasks = new ArrayList<>();

    public DatasetStored() {}

    public DatasetStored(File datasetFile) {
        data = convertFileToBytes(datasetFile);
    }

    private byte[] convertFileToBytes(File datasetFile) {
        // TODO как сделать правильнее?
        try {
            return Files.readAllBytes(datasetFile.toPath());
        } catch (IOException e) {
            System.out.println("Impossible to readAllBytes");
            return null;
        }
    }

    // Dataset Id
    public Long getDatasetId() {
        return datasetId;
    }

    void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    // Data
    public byte[] getData() {
        return data;
    }

    void setData(byte[] data) {
        this.data = data;
    }

    // Tasks
    public List<TaskQueued> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskQueued> tasks) {
        this.tasks = tasks;
    }

    //
    // public void addTask(TaskQueued task) {
    //     tasks.add(task);
    //     task.setDatasetStored(this);
    // }
    //
    // public void removeTask(TaskQueued task) {
    //     tasks.remove(task);
    //     task.setDatasetStored(null);
    // }
}
