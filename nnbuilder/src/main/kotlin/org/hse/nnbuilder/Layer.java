package org.hse.nnbuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public abstract class Layer {
    enum LayerType {
        INPUT,
        HIDDEN,
        OUTPUT
    }

    /*layer type*/
    LayerType type;
    /*function of activation*/
    Function<Float, Float> activationFunction;
    /*List of neurons on this layer*/
    ArrayList<Neuron> neurons;

    /// SETTERS ///
    void setActivationFunction(Function<Float, Float> f) {
        activationFunction = f;
    }

    void setNeurons(int n) {
        neurons = new ArrayList<>(n);
    }

    void setType(LayerType lType) {
        type = lType;
    }

    /// GETTERS ///
    List<Float> getNeuronsBiases() {
        return neurons.stream().map(Neuron::getBias).toList();
    }

    List<Neuron> getNeurons() {
        return neurons;
    }

    /// FUNCTIONS ///
    void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }

    void deleteLastNeuron() {
        neurons.remove(neurons.size() - 1);
    }

    void deleteIndexNeuron(int i) {
        assert (0 <= i && i < neurons.size() - 1);
        neurons.remove(i);

    }
}
