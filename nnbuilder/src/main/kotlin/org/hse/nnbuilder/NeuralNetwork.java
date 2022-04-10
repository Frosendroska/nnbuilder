package org.hse.nnbuilder;

import java.io.Serializable;
import java.util.List;
import org.hse.nnbuilder.Layer.ActivationFunction;

/**
 * https://www.asimovinstitute.org/neural-network-zoo/
 * Site that can help to specify Neural Network
 */
public abstract class NeuralNetwork implements Serializable {
    enum NetworkType {
        FF, // Feed Forward (Default is actually Perception)
        RNN, // Recurrent Neural Network
        LSTM, // Long Term Memory
        DCN, // Deep Convolutional Network
    }

    /* Type of Neural Network */
    NetworkType nnType;
    /* List of layers */
    List<Layer> layers;

    /**
     * @return json with full description of Neural Network
     */
    abstract List<Layer> getNeuralNetworkInformation();

    /**
     * Add new layer
     */
    abstract void addLayer(int i, Layer.LayerType lType) throws Exception;

    /**
     * @param i The index of layer to be deleted
     * Delete new layer
     */
    abstract void delLayer(int i);

    /**
     * @param i Index of the layer
     * @param f New function
     * Set another activation function on i'th layer
     */
    abstract void changeActivationFunction(int i, ActivationFunction f);

    /**
     * @param i Index of the layer
     * Add neuron to i'th layer
     */
    abstract void addNeuron(int i);

    /**
     * @param i Index of the layer
     * Delete neuron on i'th layer
     */
    abstract void delNeuron(int i);
}
