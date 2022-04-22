package org.hse.nnbuilder.nn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import net.bytebuddy.utility.dispatcher.JavaDispatcher.Container;
import org.hse.nnbuilder.services.Nnmodification.ActivationFunction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.hse.nnbuilder.services.Nnmodification.NetworkType;

@Entity
abstract class AbstractNeuralNetwork implements NeuralNetwork {
    /* Type of Neural Network */
    @Transient
    NetworkType nnType;
    /* List of layers */
    @Transient
    List<Layer> layers;
    /* Learning rate */
    @Transient
    float learningRate;
    /* Param for constructor for default network */
    @Transient
    int defaultNumberOfLayers;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    final public NetworkType getNNType() {
        return nnType;
    }

    @Override
    final public void setNNType(NetworkType nnType) {
        this.nnType = nnType;
    }

    @Override
    final public List<Layer> getLayers() {
        return layers;
    }

    @Override
    final public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    @Override
    final public int getDefaultNumberOfLayers() {
        return defaultNumberOfLayers;
    }

    @Override
    final public void setDefaultNumberOfLayers(int defaultNumberOfLayers) {
        this.defaultNumberOfLayers = defaultNumberOfLayers;
    }

    @Override
    final public float getLearningRate() {
        return learningRate;
    }

    @Override
    final public void setLearningRate(float learningRate) {
        this.learningRate = learningRate;
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
    final public String NeuralNetworkInformation() {
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
        return jsonNeuralNetwork.toString();
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
