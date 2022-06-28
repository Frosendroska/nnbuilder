package org.hse.nnbuilder.nn;

import java.util.ArrayList;
import org.hse.nnbuilder.services.Enums.NetworkType;
import org.hse.nnbuilder.services.Nnmodification.LayerType;

/**
 * Cells: [0]Input | [1]Kernel | [2..i]ConvolutionOrPool | [i...n-2]Hidden | [n-1]Output
 * <p>
 * They are primarily used for image processing but can also be used for other
 * types of input such as audio. A typical use case for CNNs is where you
 * feed the network images and the network classifies the data, e.g. it outputs
 * “cat” if you give it a cat picture and “dog” when you give it a dog picture.
 */
public class ConvolutionalNN extends AbstractNeuralNetwork {

    /* Create empty Convolutional Neural Network */
    ConvolutionalNN() {
        nnType = NetworkType.CNN;
        layers = new ArrayList<>();
        defaultNumberOfLayers = 8;
    }

    public ConvolutionalNN(ConvolutionalNN other) {
        init(other);
    }

    /* Build default Convolutional Neural Network */
    public static ConvolutionalNN buildDefaultConvolutionalNN() {
        ConvolutionalNN nn = new ConvolutionalNN();
        nn.learningRate = 0.01f;
        nn.layers.add(new Layer(5, LayerType.InputCell));
        nn.layers.add(new Layer(5, LayerType.Kernel));
        nn.layers.add(new Layer(4, LayerType.ConvolutionalOrPool));
        nn.layers.add(new Layer(3, LayerType.ConvolutionalOrPool));
        nn.layers.add(new Layer(2, LayerType.ConvolutionalOrPool));
        nn.layers.add(new Layer(4, LayerType.HiddenCell));
        nn.layers.add(new Layer(4, LayerType.HiddenCell));
        nn.layers.add(new Layer(3, LayerType.OutputCell));
        /* assert (nn.layers.size() == nn.defaultNumberOfLayers); */
        return nn;
    }

    @Override
    public void addLayer(int i, LayerType lType) throws IllegalArgumentException {
        // It is possible to add a layer with index [1...n-1]
        /* assert (0 < i && i < layers.size()); */
        int j = 0;
        for (; j < layers.size(); j++) {
            if (layers.get(j).getLayerType() == LayerType.HiddenCell) {
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
    }
}
