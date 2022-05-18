package org.hse.nnbuilder.nn.store;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hse.nnbuilder.nn.AbstractNeuralNetwork;
import org.hse.nnbuilder.queue.QueuedTask;

@Entity
@Table(name = "neuralnetworks")
public final class NeuralNetworkStored {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nnId;

    @Convert(converter = AbstractNeuralNetworkConverter.class)
    @Column(name = "content", columnDefinition = "text")
    private AbstractNeuralNetwork neuralNetwork = null;

    @OneToMany(
            // mappedBy = "neuralNetworkStored",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<QueuedTask> tasks=new ArrayList<>();

    public NeuralNetworkStored() {}

    public NeuralNetworkStored(AbstractNeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    // Id
    public Long getNnId() {
        return nnId;
    }

    public void setNnId(Long nnId) {
        this.nnId = nnId;
    }

    // Abstract Neural Network
    public AbstractNeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    private void setNeuralNetwork(AbstractNeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    // Tasks
    public List<QueuedTask> getTasks() {
        return tasks;
    }
    public void setTasks(List<QueuedTask> tasks) {
        this.tasks = tasks;
    }

    //
    public void addTask(QueuedTask task) {
        tasks.add(task);
        task.setNeuralNetworkStored(this);
    }
    public void removeTask(QueuedTask task) {
        tasks.remove(task);
        task.setNeuralNetworkStored(null);
    }
}
