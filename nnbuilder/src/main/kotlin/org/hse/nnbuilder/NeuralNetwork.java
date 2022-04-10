package org.hse.nnbuilder;

import java.util.List;

public abstract class NeuralNetwork {
    List<Layer> layers;

    /// GETTERS ///
    List<Layer> getLayers() {
        return layers;
    }
}
