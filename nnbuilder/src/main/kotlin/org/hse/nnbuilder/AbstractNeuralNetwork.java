package org.hse.nnbuilder;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractNeuralNetwork implements Serializable {
    // вложенный класс
    class Layer {
        enum LayerType {
            INPUT,
            OUTPUT,
            HIDDEN
        }
    }
    private List<Layer> layers;
}
