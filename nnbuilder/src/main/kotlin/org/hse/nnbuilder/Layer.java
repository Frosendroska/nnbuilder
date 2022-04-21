package org.hse.nnbuilder;

import java.io.Serializable;
import org.hse.nnbuilder.services.Nnmodification.*;
import org.json.JSONObject;

class Layer implements Serializable {

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
        this(1, ActivationFunction.None, t);
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
    String getLayerInformation() {
        JSONObject jsonLayer = new JSONObject();
        jsonLayer.put("Number Of Neurons", neurons);
        jsonLayer.put("Type Of Layer", lType);
        jsonLayer.put("Activation Function", activationFunction);

        return jsonLayer.toString();
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
