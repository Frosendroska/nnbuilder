package org.hse.nnbuilder;

import java.util.ArrayList;
import java.util.List;
import org.hse.nnbuilder.Layer.LayerType;

/**
 * Cells: [0]Input | [1..n-2]Hidden | [n-1]Output
 */
public class LongTermMemoryNN extends RecurrentNN {
    LongTermMemoryNN() {
        nnType = NetworkType.LSTM;
        layers = new ArrayList<>();
        layers.add(new Layer(3, LayerType.InputCell));
        layers.add(new Layer(3, LayerType.MemoryCell));
        layers.add(new Layer(3, LayerType.MemoryCell));
        layers.add(new Layer(3, LayerType.OutputCell));
        assert (layers.size() == defaultNumberOfLayers);
    }
}
