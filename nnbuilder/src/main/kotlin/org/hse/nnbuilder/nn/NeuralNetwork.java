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
     *
     */
    abstract void setLayers(List<Layer> layers);

    /**
     *
     */
    abstract void setLearningRate(float learningRate);

    /**
     *
     */
    abstract void setDefaultNumberOfLayers(int defaultNumberOfLayers);

    /**
     *
     */
    abstract void setNNType(NetworkType nnType);

    /**
     * @return Type of network
     */
    abstract NetworkType getNNType();

    /**
     * @return List of layers
     */
    abstract List<Layer> getLayers();

    /**
     * @return Learning rate
     */
    abstract float getLearningRate();

    /**
     * @return Get default number of layers
     */
    abstract int getDefaultNumberOfLayers();

    /**
     * @return json with full description of Neural Network
     */
    abstract String NeuralNetworkInformation();

    /**
     * Add new layer
     */
    abstract void addLayer(int i, LayerType lType) throws IllegalArgumentException;

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
