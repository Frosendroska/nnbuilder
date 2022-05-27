package org.hse.nnbuilder.nn;

import java.io.Serializable;
import org.hse.nnbuilder.services.Nnmodification.ActivationFunction;
import org.hse.nnbuilder.services.Nnmodification.LayerType;

public final class Layer implements Serializable {

    /* List of neurons on this layer */
    private long neurons;
    /* Layer type */
    private final LayerType layerType;
    /* Function for activation */
    private ActivationFunction activationFunction;

    public Layer() {
        this.layerType = LayerType.Undefined;
    }

    public Layer(int n, ActivationFunction f, LayerType t) {
        neurons = n;
        layerType = t;
        activationFunction = f;
    }

    public Layer(int n, LayerType t) {
        this(n, ActivationFunction.None, t);
    }

    public Layer(LayerType t) {
        this(1, ActivationFunction.None, t);
    }

    public LayerType getLayerType() {
        return layerType;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public long getNeurons() {
        return neurons;
    }

    /**
     * @param n Current number of neurons in this layer
     *          Set new number if neurons
     */
    public void changeNumberOfNeuron(long n) {
        /* assert (n > 0); */
        neurons = n;
    }

    /**
     * @param f New function
     *          Set another activation function
     */
    public void setActivationFunction(ActivationFunction f) {
        activationFunction = f;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Layer)) {
            return false;
        }
        return layerType != null &&
                layerType.equals(((Layer) o).layerType) &&
                activationFunction != null &&
                activationFunction.equals(((Layer) o).activationFunction) &&
                neurons == ((Layer) o).neurons;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
