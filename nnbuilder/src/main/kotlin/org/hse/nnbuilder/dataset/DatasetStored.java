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
import org.hse.nnbuilder.exception.DatasetNotFoundException;
import org.hse.nnbuilder.queue.TaskQueued;

@Entity
@Table(name = "datasets")
public class DatasetStored {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dsId;

    @Column
    private byte[] content;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<TaskQueued> tasks = new ArrayList<>();

    public DatasetStored() {}

    public DatasetStored(File datasetFile) throws DatasetNotFoundException {
        content = convertFileToBytes(datasetFile);
    }

    private byte[] convertFileToBytes(File datasetFile) throws DatasetNotFoundException {
        try {
            return Files.readAllBytes(datasetFile.toPath());
        } catch (IOException e) {
           throw new DatasetNotFoundException(e, "Error in converting dataset to the array of bytes");
        }
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
