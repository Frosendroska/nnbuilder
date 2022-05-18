package org.hse.nnbuilder.nn.store;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hse.nnbuilder.nn.AbstractNeuralNetwork;
import org.hse.nnbuilder.queue.TasksQueue;

@Entity
@Table(name = "neuralnetwork")
public final class NeuralNetworkStored {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long nnId;

    @Convert(converter = AbstractNeuralNetworkConverter.class)
    @Column(name = "content", columnDefinition = "text")
    private AbstractNeuralNetwork neuralNetwork = null;

    @OneToOne(mappedBy = "neuralNetworkStored")
    TasksQueue tasksQueue;

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
}
