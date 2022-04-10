package org.hse.nnbuilder;

import java.util.List;
import org.hse.nnbuilder.Layer.ActivationFunction;
import org.hse.nnbuilder.Layer.LayerType;

public class RecurrentNN extends NeuralNetwork {

    int defaultNumberOfLayers = 4;

    RecurrentNN() {
        nnType = NetworkType.FF;
        layers.add(new Layer(3, LayerType.InputCell));
        layers.add(new Layer(3, LayerType.RecurrentCell));
        layers.add(new Layer(3, LayerType.RecurrentCell));
        layers.add(new Layer(3, LayerType.OutputCell));
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
        if (lType == LayerType.RecurrentCell) {
            layers.add(i, (new Layer(lType)));
        } else {
            throw new Exception("You can add only Recurrent layer"); // TODO Сделать нормальное исключение
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
        // Neuron can be added everywhere
        assert (0 <= i && i < layers.size());
        layers.get(i).addNeuron();
    }

    @Override
    void delNeuron(int i) {
        // Neuron can be deleted from everywhere
        assert (0 <= i && i < layers.size());
        layers.get(i).deleteNeuron();
    }

}
