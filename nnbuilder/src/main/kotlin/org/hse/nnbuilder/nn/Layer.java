package org.hse.nnbuilder.nn;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hse.nnbuilder.services.Nnmodification.*;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Entity
class Layer implements Serializable {

    /* List of neurons on this layer */
    @Transient
    private int neurons;
    /* Layer type */
    @Transient
    private final LayerType lType;
    /* Function for activation */
    @Transient
    private ActivationFunction activationFunction;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Layer() {
        this.lType = LayerType.Undefined;
    }

    public Layer(int n, ActivationFunction f, LayerType t) {
        neurons = n;
        lType = t;
        activationFunction = f;
    }

    public Layer(int n, LayerType t) {
        this(n, ActivationFunction.None, t);
    }

    public Layer(LayerType t) {
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

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
