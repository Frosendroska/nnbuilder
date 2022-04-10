package org.hse.nnbuilder;

import java.io.Serializable;

class Layer implements Serializable {
    enum LayerType {
        InputCell,
        BackfedInputCell,
        NoisyInputCell,
        HiddenCell,
        ProbablisticHiddenCell,
        SpikingHiddenCell,
        CapculeCell,
        OutputCell,
        MatchInputOutputCell,
        RecurrentCell,
        MemoryCell,
        GatedMemoryCell,
        Kernel,
        ConvolutionalOrPool
    }

    /**
     * https://towardsdatascience.com/activation-functions-neural-networks-1cbd9f8d91d6
     * Site with most used Activation functions
     */
    enum ActivationFunction {
        None,
        Linear,
        Sigmoid,
        Tanh,
        ReLU,
        LeakyReLU,
        Max,
        BinaryStep,
        Gaussian
    }

    /* Layer type */
    private final LayerType lType;
    /* Function for activation */
    private ActivationFunction activationFunction;
    /* List of neurons on this layer */
    private int neurons;

    Layer(ActivationFunction f, int n, LayerType t) {
        lType = t;
        activationFunction = f;
        neurons = n;
    }

    Layer(int i, LayerType t) {
        this(ActivationFunction.None, i, t);
    }

    Layer(LayerType t) {
        this(ActivationFunction.None, 1, t);
    }

    /**
     * @return json with full description of Layer
     */
    Layer getLayerInformation() {
        // TODO Понять, как сериализовывать для передачи пайторчу
        return null;
    }

    /**
     * Increase the number of neurons in layer
     */
    void addNeuron() {
        assert (neurons >= 0);
        neurons++;
    }

    /**
     * Decrease the number of neurons in layer
     */
    void deleteNeuron() {
        if (neurons >= 0) {
            neurons--;
        }
    }

    /**
     * @param f New function
     * Set another activation function
     */
    void setActivationFunction(ActivationFunction f) {
        activationFunction = f;
    }
}
