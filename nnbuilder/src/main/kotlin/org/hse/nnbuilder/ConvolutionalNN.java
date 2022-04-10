package org.hse.nnbuilder;

import java.util.List;
import org.hse.nnbuilder.Layer.ActivationFunction;
import org.hse.nnbuilder.Layer.LayerType;

public class ConvolutionalNN extends NeuralNetwork {
    @Override
    List<Layer> getNeuralNetworkInformation() {
        // TODO Понять, как сериализовывать для передачи пайторчу
        return null;
    }

    @Override
    void addLayer(int i, LayerType lType) throws Exception {

    }

    @Override
    void delLayer(int i) {

    }

    @Override
    void changeActivationFunction(int i, ActivationFunction f) {

    }

    @Override
    void addNeuron(int i) {

    }

    @Override
    void delNeuron(int i) {

    }

}
