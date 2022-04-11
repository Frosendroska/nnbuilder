package org.hse.nnbuilder;

import java.util.ArrayList;
import java.util.List;
import org.hse.nnbuilder.Layer.LayerType;

/**
 * Cells: [0]Input | [1]Kernel | [2..i]ConvolutionOrPool | [i...n-2]Hidden | [n-1]Output
 * <p>
 * They are primarily used for image processing but can also be used for other
 * types of input such as audio. A typical use case for CNNs is where you
 * feed the network images and the network classifies the data, e.g. it outputs
 * “cat” if you give it a cat picture and “dog” when you give it a dog picture.
 */
public class ConvolutionalNN extends AbstractNeuralNetwork {

    /* Param for constructor for default network */
    final int defaultNumberOfLayers = 8;

    ConvolutionalNN() {
        nnType = NetworkType.CNN;
        layers = new ArrayList<>();
        layers.add(new Layer(5, LayerType.InputCell));
        layers.add(new Layer(5, LayerType.Kernel));
        layers.add(new Layer(4, LayerType.ConvolutionalOrPool));
        layers.add(new Layer(3, LayerType.ConvolutionalOrPool));
        layers.add(new Layer(2, LayerType.ConvolutionalOrPool));
        layers.add(new Layer(4, LayerType.HiddenCell));
        layers.add(new Layer(4, LayerType.HiddenCell));
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
        int j = 0; // TODO написать красиво стримом
        for (; j < layers.size(); j++) {
            if (layers.get(j).lType == LayerType.HiddenCell) {
                break;
            }
        }
        if (lType == LayerType.ConvolutionalOrPool) {
            if (i < j) {
                layers.add(i, (new Layer(lType)));
            } else {
                throw new IllegalArgumentException("Cat not add Convolutional layer here");
            }
        } else if (lType == LayerType.HiddenCell) {
            if (i >= j) {
                layers.add(i, (new Layer(lType)));
            } else {
                throw new IllegalArgumentException("Cat not add Hidden layer here");
            }
        } else {
            throw new IllegalArgumentException("You can add only Hidden and Convolutional layer");
        }
        assert true;
    }

}
