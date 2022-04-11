package org.hse.nnbuilder;

import java.util.ArrayList;
import java.util.List;
import org.hse.nnbuilder.Layer.ActivationFunction;
import org.hse.nnbuilder.Layer.LayerType;

/**
 * Cells: [0]Input | [1..n-2]Hidden | [n-1]Output
 */
public class RecurrentNN extends AbstractNeuralNetwork {

    /* Param for constructor for default network */
    final int defaultNumberOfLayers = 4;

    RecurrentNN() {
        nnType = NetworkType.RNN;
        layers = new ArrayList<>();
        layers.add(new Layer(3, LayerType.InputCell));
        layers.add(new Layer(3, LayerType.RecurrentCell));
        layers.add(new Layer(3, LayerType.RecurrentCell));
        layers.add(new Layer(3, LayerType.OutputCell));
        assert (layers.size() == defaultNumberOfLayers);
    }

    @Override
    public List<Layer> getNeuralNetworkInformation() {
        // TODO Понять, как сериализовывать для передачи пайторчу
        return null;
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
