package org.hse.nnbuilder.services;

import java.util.ArrayList;
import org.hse.nnbuilder.services.Layer.LayerType;

/**
 * Cells: [0]Input | [1..n-2]Hidden | [n-1]Output
 */
class FastForwardNN extends AbstractNeuralNetwork {

    /* Param for constructor for default network */
    private static final int defaultNumberOfLayers = 2;

    /* Create empty Fast Forward Neural Network */
    FastForwardNN() {
        layers = new ArrayList<>();
        nnType = NetworkType.FF;
    }

    /* Build default Fast Forward Neural Network */
    public static FastForwardNN buildDefaultFastForwardNN() {
        FastForwardNN nn = new FastForwardNN();
        nn.learningRate = 0.01f;
        nn.layers.add(new Layer(2, LayerType.InputCell));
        nn.layers.add(new Layer(1, LayerType.OutputCell));
        assert (nn.layers.size() == defaultNumberOfLayers);
        return nn;
    }

    @Override
    public void addLayer(int i, LayerType lType) throws IllegalArgumentException {
        // It is possible to add a layer with index [1...n-1]
        assert (0 < i && i < layers.size());
        if (lType == LayerType.HiddenCell) {
            layers.add(i, new Layer(lType));
        } else {
            throw new IllegalArgumentException("You can add only Hidden layer");
        }
        assert true;
    }
}
