package org.hse.nnbuilder;

import java.util.List;
import org.hse.nnbuilder.Layer.ActivationFunction;
import org.hse.nnbuilder.Layer.LayerType;

/**
 * Cells: [0]Input | [1..n-2]Hidden | [n-1]Output
 */
public class FastForwardNN extends NeuralNetwork {

    int defaultNumberOfLayers = 2;

    FastForwardNN() {
        nnType = NetworkType.FF;
        layers.add(new Layer(2, LayerType.InputCell));
        layers.add(new Layer(1, LayerType.OutputCell));
        assert (layers.size() == defaultNumberOfLayers);
    }

    @Override
    List<Layer> getNeuralNetworkInformation() {
        // TODO Понять, как сериализовывать для передачи пайторчу
        return null;
    }

    @Override
    void addLayer(int i, LayerType lType) throws Exception {
        assert (0 < i && i < layers.size());
        if (lType == LayerType.HiddenCell) {
            layers.add(i, (new Layer(lType)));
        } else {
            throw new Exception("You can add only Hidden layer"); // TODO Сделать нормальное исключение
        }
        assert true;
    }

    @Override
    void delLayer(int i) {
        assert (0 < i && i < layers.size() - 1);
        layers.remove(i);
    }

    @Override
    void changeActivationFunction(int i, ActivationFunction f) {
        // Activation function exists only in Hidden layers
        assert (0 < i && i < layers.size() - 1);
        layers.get(i).setActivationFunction(f);
    }

    @Override
    void addNeuron(int i) {
        // Neuron can be added everywhere except output layer
        assert (0 <= i && i < layers.size() - 1);
        layers.get(i).addNeuron();
    }

    @Override
    void delNeuron(int i) {
        // Neuron can be deleted from everywhere except output layer
        assert (0 <= i && i < layers.size() - 1);
        layers.get(i).deleteNeuron();
    }
}
