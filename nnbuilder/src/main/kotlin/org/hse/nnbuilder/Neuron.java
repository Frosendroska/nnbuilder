package org.hse.nnbuilder;

import java.util.List;

public abstract class Neuron {
    /*Default weight*/
    float bias;
    /*connected neurons next layer*/
    List<Neuron> connectedNeuronsNextLayer;

    //
    float getBias() {
        return bias;
    }
}
