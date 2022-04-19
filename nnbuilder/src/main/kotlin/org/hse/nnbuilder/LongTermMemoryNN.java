package org.hse.nnbuilder;

import java.util.ArrayList;
import java.util.List;
import org.hse.nnbuilder.Layer.LayerType;

/**
 * Cells: [0]Input | [1..n-2]Hidden | [n-1]Output
 */
public class LongTermMemoryNN extends RecurrentNN {

    /* Create empty Long Short Term Memory Network */
    LongTermMemoryNN() {
        nnType = NetworkType.LSTM;
        layers = new ArrayList<>();
    }

    /* Build default Long Short Term Memory Neural Network */
    public static LongTermMemoryNN buildDefaultLongTermMemoryNN() {
        LongTermMemoryNN nn = new LongTermMemoryNN();
        nn.learningRate = 0.01f;
        nn.layers.add(new Layer(3, LayerType.InputCell));
        nn.layers.add(new Layer(3, LayerType.MemoryCell));
        nn.layers.add(new Layer(3, LayerType.MemoryCell));
        nn.layers.add(new Layer(3, LayerType.OutputCell));
        assert (nn.layers.size() == defaultNumberOfLayers);
        return nn;
    }
}
