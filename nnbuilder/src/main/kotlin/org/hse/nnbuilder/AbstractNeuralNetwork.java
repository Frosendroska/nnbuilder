package org.hse.nnbuilder;

import java.util.List;
import org.hse.nnbuilder.Layer.ActivationFunction;

public abstract class AbstractNeuralNetwork implements NeuralNetwork {
    /* Type of Neural Network */
    NetworkType nnType;
    /* List of layers */
    List<Layer> layers;
    /* Param for constructor for default network */
    int defaultNumberOfLayers;

    @Override
    public NetworkType getNNType() {
        return nnType;
    }

    @Override
    public List<Layer> getLayers() {
        return layers;
    }

    @Override
    public int getDefaultNumberOfLayers() {
        return defaultNumberOfLayers;
    }

    @Override
    public void delLayer(int i) {
        // It is possible to delete only hidden layers
        assert (0 < i && i < layers.size() - 1);
        layers.remove(i);
    }

    @Override
    public void changeActivationFunction(int i, ActivationFunction f) {
        // Activation function exists only in Hidden layers
        assert (0 < i && i < layers.size() - 1);
        layers.get(i).setActivationFunction(f);
    }

    @Override
    public void changeNumberOfNeuron(int i, int n) {
        // Neuron can be added everywhere (input is fixed)
        assert (0 < i && i < layers.size());
        layers.get(i).changeNumberOfNeuron(n);
    }
}
