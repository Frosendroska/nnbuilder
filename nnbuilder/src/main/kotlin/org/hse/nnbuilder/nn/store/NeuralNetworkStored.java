package org.hse.nnbuilder.nn.store;

import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hse.nnbuilder.nn.AbstractNeuralNetwork;

@Entity
@Table(name = "neuralnetwork")
public final class NeuralNetworkStored {

    @Id
    private Long nnId = new Random().nextLong();


    @Convert(converter = AbstractNeuralNetworkConverter.class)
    @Column(name = "content", columnDefinition = "text")
    private AbstractNeuralNetwork neuralNetwork = null;

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
