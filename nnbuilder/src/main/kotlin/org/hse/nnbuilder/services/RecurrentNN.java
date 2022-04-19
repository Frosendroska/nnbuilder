package org.hse.nnbuilder.services;

import java.util.ArrayList;
import org.hse.nnbuilder.services.Layer.LayerType;

/**
 * Cells: [0]Input | [1..n-2]Hidden | [n-1]Output
 */
public class RecurrentNN extends AbstractNeuralNetwork {

    /* Param for constructor for default network */
    static final int defaultNumberOfLayers = 4;

    /* Create empty Recurrent Neural Network */
    RecurrentNN() {
        nnType = NetworkType.RNN;
        layers = new ArrayList<>();
    }

    /* Build default Recurrent Neural Network */
    public static RecurrentNN buildDefaultRecurrentNN() {
        RecurrentNN nn = new RecurrentNN();
        nn.learningRate = 0.01f;
        nn.layers.add(new Layer(3, LayerType.InputCell));
        nn.layers.add(new Layer(3, LayerType.InputCell));
        nn.layers.add(new Layer(3, LayerType.RecurrentCell));
        nn.layers.add(new Layer(3, LayerType.RecurrentCell));
        nn.layers.add(new Layer(3, LayerType.OutputCell));
        assert (nn.layers.size() == defaultNumberOfLayers);
        return nn;
    }

    @Override
    public void addLayer(int i, LayerType lType) throws IllegalArgumentException {
        // It is possible to add a layer with index [1...n-1]
        assert (0 < i && i < layers.size());
        if (lType == LayerType.RecurrentCell || lType == LayerType.MemoryCell) {
            layers.add(i, (new Layer(lType)));
        } else {
            throw new IllegalArgumentException("You can add only Recurrent or Memory layer");
        }
        assert true;
    }

}
