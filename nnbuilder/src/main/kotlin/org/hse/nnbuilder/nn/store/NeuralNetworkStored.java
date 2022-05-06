package org.hse.nnbuilder.nn.store;

import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hse.nnbuilder.nn.AbstractNeuralNetwork;
import org.hse.nnbuilder.version_controller.GeneralNeuralNetwork;

@Entity
@Table(name = "neuralnetwork")
public final class NeuralNetworkStored {

    @Id
    private Long id = new Random().nextLong();

    @Convert(converter = AbstractNeuralNetworkConverter.class)
    @Column(name = "content", columnDefinition = "text")
    private AbstractNeuralNetwork neuralNetwork = null;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "general_neural_network_id", nullable = false)
    public GeneralNeuralNetwork generalNeuralNetwork;

    public NeuralNetworkStored() {
    }

    // //FOR TESTS
    // public NeuralNetworkStored(AbstractNeuralNetwork neuralNetwork) {
    //     this.neuralNetwork = neuralNetwork;
    // }

    public NeuralNetworkStored(AbstractNeuralNetwork neuralNetwork, GeneralNeuralNetwork generalNeuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        this.generalNeuralNetwork = generalNeuralNetwork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AbstractNeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    private void setNeuralNetwork(AbstractNeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }
}
