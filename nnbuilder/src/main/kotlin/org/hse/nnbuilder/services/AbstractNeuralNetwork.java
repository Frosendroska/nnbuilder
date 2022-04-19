package org.hse.nnbuilder.services;

import java.util.List;
import org.hse.nnbuilder.services.Layer.ActivationFunction;
import org.json.JSONArray;
import org.json.JSONObject;

abstract class AbstractNeuralNetwork implements NeuralNetwork {
    /* Type of Neural Network */
    NetworkType nnType;
    /* List of layers */
    List<Layer> layers;
    /* Learning rate */
    float learningRate;
    /* Param for constructor for default network */
    private int defaultNumberOfLayers;

    @Override
    final public NetworkType getNNType() {
        return nnType;
    }

    @Override
    final public List<Layer> getLayers() {
        return layers;
    }

    @Override
    final public int getDefaultNumberOfLayers() {
        return defaultNumberOfLayers;
    }

    @Override
    final public float getLearningRate() {
        return learningRate;
    }

    @Override
    final public void delLayer(int i) {
        // It is possible to delete only hidden layers
        assert (0 < i && i < layers.size() - 1);
        layers.remove(i);
    }

    @Override
    final public void changeActivationFunction(int i, ActivationFunction f) {
        // Activation function exists only in Hidden layers
        assert (0 < i && i < layers.size() - 1);
        layers.get(i).setActivationFunction(f);
    }

    @Override
    final public void changeNumberOfNeuron(int i, int n) {
        // Neuron can be added everywhere (input is fixed)
        assert (0 < i && i < layers.size());
        layers.get(i).changeNumberOfNeuron(n);
    }

    @Override
    final public String getNeuralNetworkInformation() {
        JSONObject jsonNeuralNetwork = new JSONObject();
        // Put a type
        jsonNeuralNetwork.put("NetworkType", nnType.toString());
        // Put layers to JsonArray
        JSONArray jsonLayers = new JSONArray();
        for (var l : layers) {
            jsonLayers.put(l.getLayerInformation());
        }
        // Put JsonArray to JSONObject
        jsonNeuralNetwork.put("Layers", jsonLayers);
        return null;
    }
}
