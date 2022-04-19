package org.hse.nnbuilder.services;

import org.hse.nnbuilder.services.NeuralNetwork.NetworkType;
import org.json.JSONObject;

class Layer {
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

    /* List of neurons on this layer */
    private int neurons;
    /* Layer type */
    private final LayerType lType;
    /* Function for activation */
    private ActivationFunction activationFunction;

    Layer(int n, ActivationFunction f, LayerType t) {
        neurons = n;
        lType = t;
        activationFunction = f;
    }

    Layer(int n, LayerType t) {
        this(n, ActivationFunction.None, t);
    }

    Layer(LayerType t) {
        this(1, ActivationFunction.None,  t);
    }

    LayerType getLayerType() {
        return lType;
    }

    ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    int getNeurons() {
        return neurons;
    }

    /**
     * @return json string with full description of Layer
     */
    JSONObject getLayerInformation() {
        return new JSONObject(this);
    }

    /**
     * @param n Current number of neurons in this layer
     * Set new number if neurons
     */
    void changeNumberOfNeuron(int n) {
        assert (n > 0);
        neurons = n;
    }

    /**
     * @param f New function
     * Set another activation function
     */
    void setActivationFunction(ActivationFunction f) {
        activationFunction = f;
    }
}
