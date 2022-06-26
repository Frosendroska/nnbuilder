package org.hse.nnbuilder.nn;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import java.util.LinkedList;
import java.util.List;
import org.hse.nnbuilder.modification_types.Modification;
import org.hse.nnbuilder.services.Nnmodification.ActivationFunction;
import org.hse.nnbuilder.services.Nnmodification.NetworkType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXISTING_PROPERTY, property = "nntype")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ConvolutionalNN.class, name = "CNN"),
    @JsonSubTypes.Type(value = FeedForwardNN.class, name = "FF"),
    @JsonSubTypes.Type(value = LongShortTermMemoryNN.class, name = "LSTM"),
    @JsonSubTypes.Type(value = RecurrentNN.class, name = "RNN"),
})
public abstract class AbstractNeuralNetwork implements NeuralNetwork {
    /**
     * Type of Neural Network
     */
    @JsonProperty("nntype")
    NetworkType nnType;
    /**
     * List of layers
     */
    List<Layer> layers;
    /**
     * Learning rate
     */
    float learningRate;
    /**
     * Param for constructor for default network
     */
    int defaultNumberOfLayers;
    /**
     * History of small changes
     */
    private List<Modification> modifications = new LinkedList<>();
    /**
     * Index in modifications showing the current nn version
     */
    private int currentVersionPointer = -1;

    protected void init(AbstractNeuralNetwork other) {
        this.nnType = other.getNNType();
        this.layers = other.getLayers();
        this.learningRate = other.getLearningRate();
        this.defaultNumberOfLayers = other.getDefaultNumberOfLayers();
    }

    @Override
    public final NetworkType getNNType() {
        return nnType;
    }

    @Override
    public final List<Layer> getLayers() {
        return layers;
    }

    @Override
    public final int getDefaultNumberOfLayers() {
        return defaultNumberOfLayers;
    }

    @Override
    public final float getLearningRate() {
        return learningRate;
    }

    @Override
    public final void delLayer(int i) {
        // It is possible to delete only hidden layers
        /* assert (0 < i && i < layers.size() - 1); */
        layers.remove(i);
    }

    @Override
    public final void changeActivationFunction(int i, ActivationFunction f) {
        // Activation function exists only in Hidden layers
        /* assert (0 < i && i < layers.size() - 1); */
        layers.get(i).setActivationFunction(f);
    }

    @Override
    public final void changeNumberOfNeuron(int i, long n) {
        // Neuron can be added everywhere (input is fixed)
        /* assert (0 < i && i < layers.size()); */
        layers.get(i).changeNumberOfNeuron(n);
    }

    public void addNewModification(Modification modification) {
        modifications = modifications.subList(0, currentVersionPointer + 1);
        modifications.add(modification);
        currentVersionPointer++;
    }

    public void undo() {
        if (currentVersionPointer < 0) {
            throw new IllegalArgumentException("There are no previous chenges.");
        }
        modifications.get(currentVersionPointer).makeReverseChange(this);
        currentVersionPointer--;
    }

    public void redo() {
        if (currentVersionPointer == modifications.size() - 1) {
            throw new IllegalArgumentException("There are no subsequent chenges.");
        }
        currentVersionPointer++;
        modifications.get(currentVersionPointer).makeDirectChange(this);
    }
}
