package org.hse.nnbuilder;

import java.io.Serializable;
import java.util.List;
import org.hse.nnbuilder.Layer.ActivationFunction;

/**
 * https://www.asimovinstitute.org/neural-network-zoo/
 * Site that can help to specify Neural Network
 */
interface NeuralNetwork extends Serializable {
    enum NetworkType {
        FF, // Feed Forward (Default is actually Perception)
        RNN, // Recurrent Neural Network
        LSTM, // Long Term Memory
        CNN, // Deep Convolutional Network
    }

    /**
     * @return Type of network
     */
    abstract NetworkType getNNType();

    /**
     * @return Type of network
     */
    abstract List<Layer> getLayers();

    /**
     * @return Get default number of layers
     */
    abstract int getDefaultNumberOfLayers();

    /**
     * @return json with full description of Neural Network
     */
    abstract List<Layer> getNeuralNetworkInformation();

    /**
     * Add new layer
     */
    abstract void addLayer(int i, Layer.LayerType lType) throws IllegalArgumentException;

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
     * @param n New number of neurons
     * Add neuron to i'th layer
     */
    abstract void changeNumberOfNeuron(int i, int n);

}
