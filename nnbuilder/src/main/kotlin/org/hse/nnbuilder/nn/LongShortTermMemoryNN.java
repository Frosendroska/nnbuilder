package org.hse.nnbuilder.nn;

import java.util.ArrayList;
import org.hse.nnbuilder.services.Enums.NetworkType;
import org.hse.nnbuilder.services.Nnmodification.LayerType;

/**
 * Cells: [0]Input | [1..n-2]Hidden | [n-1]Output
 */
public class LongShortTermMemoryNN extends RecurrentNN {

    /* Create empty Long Short Term Memory Network */
    LongShortTermMemoryNN() {
        nnType = NetworkType.LSTM;
        layers = new ArrayList<>();
        defaultNumberOfLayers = 4;
    }

    public LongShortTermMemoryNN(LongShortTermMemoryNN other) {
        init(other);
    }

    /* Build default Long Short Term Memory Neural Network */
    public static LongShortTermMemoryNN buildDefaultLongTermMemoryNN() {
        LongShortTermMemoryNN nn = new LongShortTermMemoryNN();
        nn.learningRate = 0.01f;
        nn.layers.add(new Layer(3, LayerType.InputCell));
        nn.layers.add(new Layer(3, LayerType.MemoryCell));
        nn.layers.add(new Layer(3, LayerType.MemoryCell));
        nn.layers.add(new Layer(3, LayerType.OutputCell));
        /* assert (nn.layers.size() == nn.defaultNumberOfLayers); */
        return nn;
    }
}
