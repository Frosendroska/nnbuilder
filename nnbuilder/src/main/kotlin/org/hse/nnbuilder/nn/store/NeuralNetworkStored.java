package org.hse.nnbuilder.nn.store;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hse.nnbuilder.nn.AbstractNeuralNetwork;
import org.hse.nnbuilder.queue.TaskQueued;
import org.hse.nnbuilder.version_controller.GeneralNeuralNetwork;

@Entity
@Table(name = "neuralnetworks")
public final class NeuralNetworkStored {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long nnId;

    @Convert(converter = AbstractNeuralNetworkConverter.class)
    @Column(name = "content", columnDefinition = "text")
    private AbstractNeuralNetwork neuralNetwork;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<TaskQueued> tasks = new ArrayList<>();

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "general_neural_network_id", nullable = false)
    private GeneralNeuralNetwork generalNeuralNetwork;

    public NeuralNetworkStored() {}

    // // ONLY FOR TESTS
    // public NeuralNetworkStored(AbstractNeuralNetwork neuralNetwork) {
    //     this.neuralNetwork = neuralNetwork;
    // }

    public NeuralNetworkStored(AbstractNeuralNetwork neuralNetwork, GeneralNeuralNetwork generalNeuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        this.generalNeuralNetwork = generalNeuralNetwork;
    }

    // Id
    public Long getNnId() {
        return nnId;
    }

    public void setNnId(Long nnId) {
        this.nnId = nnId;
    }

    public GeneralNeuralNetwork getGeneralNeuralNetwork() {
        return generalNeuralNetwork;
    }

    // Abstract Neural Network
    public AbstractNeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public NeuralNetworkStored getEntity() {
        return this;
    }

    public void setNeuralNetwork(AbstractNeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NeuralNetworkStored)) {
            return false;
        }
        return nnId != null && nnId.equals(((NeuralNetworkStored) o).nnId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
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
        task.setNeuralNetworkStored(this);
    }

    public void removeTask(TaskQueued task) {
        tasks.remove(task);
        task.setNeuralNetworkStored(null);
    }
}
