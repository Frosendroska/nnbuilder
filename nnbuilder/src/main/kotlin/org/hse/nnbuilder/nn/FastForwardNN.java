package org.hse.nnbuilder.nn;

import java.util.ArrayList;
import org.hse.nnbuilder.services.Nnmodification.LayerType;
import org.hse.nnbuilder.services.Nnmodification.NetworkType;

/**
 * Cells: [0]Input | [1..n-2]Hidden | [n-1]Output
 */
public class FastForwardNN extends AbstractNeuralNetwork {

    /* Create empty Fast Forward Neural Network */
    FastForwardNN() {
        layers = new ArrayList<>();
        nnType = NetworkType.FF;
        defaultNumberOfLayers = 2;
    }

    /* Build default Fast Forward Neural Network */
    public static FastForwardNN buildDefaultFastForwardNN() {
        FastForwardNN nn = new FastForwardNN();
        nn.learningRate = 0.01f;
        nn.layers.add(new Layer(2, LayerType.InputCell));
        nn.layers.add(new Layer(1, LayerType.OutputCell));
        assert (nn.layers.size() == nn.defaultNumberOfLayers);
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
