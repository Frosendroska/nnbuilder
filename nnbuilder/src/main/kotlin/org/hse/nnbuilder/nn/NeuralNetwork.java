package org.hse.nnbuilder.nn;

import java.io.Serializable;
import java.util.List;
import org.hse.nnbuilder.services.Nnmodification.ActivationFunction;
import org.hse.nnbuilder.services.Nnmodification.NetworkType;
import org.hse.nnbuilder.services.Nnmodification.LayerType;


/**
 * https://www.asimovinstitute.org/neural-network-zoo/
 * Site that can help to specify Neural Network
 */
interface NeuralNetwork extends Serializable {

    /**
     * @return Type of network
     */
    NetworkType getNNType();

    /**
     * @return List of layers
     */
    List<Layer> getLayers();

    /**
     * @return Learning rate
     */
    float getLearningRate();

    /**
     * @return Get default number of layers
     */
    int getDefaultNumberOfLayers();

    /**
     * Add new layer
     */
    void addLayer(int i, LayerType lType) throws IllegalArgumentException;

    /**
     * @param i The index of layer to be deleted
     * Delete new layer
     */
    void delLayer(int i);

    /**
     * @param i Index of the layer
     * @param f New function
     * Set another activation function on i'th layer
     */
    void changeActivationFunction(int i, ActivationFunction f);

    /**
     * @param i Index of the layer
     * @param n New number of neurons
     * Add neuron to i'th layer
     */
    void changeNumberOfNeuron(int i, int n);
}
