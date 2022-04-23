package org.hse.nnbuilder.nn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hse.nnbuilder.services.Nnmodification.ActivationFunction;
import org.hse.nnbuilder.services.Nnmodification.LayerType;
import org.hse.nnbuilder.services.Nnmodification.NetworkType;
import org.junit.jupiter.api.Test;

public class NNCreatingTests {

    //////////////////////////// FastForwardNN
    @Test
    public void buildDefaultFastForwardNNTest() throws Exception {
        FeedForwardNN nn = FeedForwardNN.buildDefaultFastForwardNN();

        // TYPE
        assertEquals(nn.getNNType(), NetworkType.FF);
        assertEquals(nn.getLayers().get(0).getLayerType(), LayerType.InputCell);
        assertEquals(nn.getLayers().get(1).getLayerType(), LayerType.OutputCell);

        // ACTIVATION FUNCTION
        assertEquals(nn.getLayers().get(0).getActivationFunction(), ActivationFunction.None);
        assertEquals(nn.getLayers().get(1).getActivationFunction(), ActivationFunction.None);

        // SIZE
        assertEquals(nn.getLayers().size(), nn.getDefaultNumberOfLayers());

        // NUMBER OF NEURONS
        assertEquals(nn.getLayers().get(0).getNeurons(), 2);
        assertEquals(nn.getLayers().get(1).getNeurons(), 1);
    }

    @Test
    public void buildEmptyForwardNNTest() throws Exception {
        FeedForwardNN nn = new FeedForwardNN();

        // TYPE
        assertEquals(nn.getNNType(), NetworkType.FF);

        // SIZE
        assertEquals(nn.getLayers().size(), 0);

        // NUMBER OF NEURONS
        assertEquals(nn.getDefaultNumberOfLayers(), 2);

    }

    //////////////////////////// ConvolutionalNN
    @Test
    public void buildDefaultConvolutionalNNTest() throws Exception {
        ConvolutionalNN nn = ConvolutionalNN.buildDefaultConvolutionalNN();

        // TYPE
        assertEquals(nn.getNNType(), NetworkType.CNN);
        assertEquals(nn.getLayers().get(0).getLayerType(), LayerType.InputCell);
        assertEquals(nn.getLayers().get(1).getLayerType(), LayerType.Kernel);
        assertEquals(nn.getLayers().get(2).getLayerType(), LayerType.ConvolutionalOrPool);
        assertEquals(nn.getLayers().get(3).getLayerType(), LayerType.ConvolutionalOrPool);
        assertEquals(nn.getLayers().get(4).getLayerType(), LayerType.ConvolutionalOrPool);
        assertEquals(nn.getLayers().get(5).getLayerType(), LayerType.HiddenCell);
        assertEquals(nn.getLayers().get(6).getLayerType(), LayerType.HiddenCell);
        assertEquals(nn.getLayers().get(7).getLayerType(), LayerType.OutputCell);

        // ACTIVATION FUNCTION
        for (int i = 0; i < 8; i++) {
            assertEquals(nn.getLayers().get(i).getActivationFunction(), ActivationFunction.None);

        }

        // SIZE
        assertEquals(nn.getLayers().size(), nn.getDefaultNumberOfLayers());

        // NUMBER OF NEURONS
        assertEquals(nn.getLayers().get(0).getNeurons(), 5);
        assertEquals(nn.getLayers().get(1).getNeurons(), 5);
        assertEquals(nn.getLayers().get(2).getNeurons(), 4);
        assertEquals(nn.getLayers().get(3).getNeurons(), 3);
        assertEquals(nn.getLayers().get(4).getNeurons(), 2);
        assertEquals(nn.getLayers().get(5).getNeurons(), 4);
        assertEquals(nn.getLayers().get(6).getNeurons(), 4);
        assertEquals(nn.getLayers().get(7).getNeurons(), 3);
    }

    @Test
    public void buildEmptyConvolutionalNNTest() throws Exception {
        ConvolutionalNN nn = new ConvolutionalNN();

        // TYPE
        assertEquals(nn.getNNType(), NetworkType.CNN);

        // SIZE
        assertEquals(nn.getLayers().size(), 0);

        // NUMBER OF NEURONS
        assertEquals(nn.getDefaultNumberOfLayers(), 8);

    }

    //////////////////////////// RecurrentNN
    @Test
    public void buildDefaultRecurrentNNTest() throws Exception {
        RecurrentNN nn = RecurrentNN.buildDefaultRecurrentNN();

        // TYPE
        assertEquals(nn.getNNType(), NetworkType.RNN);
        assertEquals(nn.getLayers().get(0).getLayerType(), LayerType.InputCell);
        assertEquals(nn.getLayers().get(1).getLayerType(), LayerType.RecurrentCell);
        assertEquals(nn.getLayers().get(2).getLayerType(), LayerType.RecurrentCell);
        assertEquals(nn.getLayers().get(3).getLayerType(), LayerType.OutputCell);


        // ACTIVATION FUNCTION
        for (int i = 0; i < 4; i++) {
            assertEquals(nn.getLayers().get(i).getActivationFunction(), ActivationFunction.None);
        }

        // SIZE
        assertEquals(nn.getLayers().size(), nn.getDefaultNumberOfLayers());

        // NUMBER OF NEURONS
        assertEquals(nn.getLayers().get(0).getNeurons(), 3);
        assertEquals(nn.getLayers().get(1).getNeurons(), 3);
        assertEquals(nn.getLayers().get(2).getNeurons(), 3);
        assertEquals(nn.getLayers().get(3).getNeurons(), 3);
    }

    @Test
    public void buildEmptyRecurrentNNTest() throws Exception {
        RecurrentNN nn = new RecurrentNN();

        // TYPE
        assertEquals(nn.getNNType(), NetworkType.RNN);

        // SIZE
        assertEquals(nn.getLayers().size(), 0);

        // NUMBER OF NEURONS
        assertEquals(nn.getDefaultNumberOfLayers(), 4);

    }


    //////////////////////////// LongTermMemoryNN
    @Test
    public void buildDefaultLongTermMemoryNNTest() throws Exception {
        LongShortTermMemoryNN nn = LongShortTermMemoryNN.buildDefaultLongTermMemoryNN();

        // TYPE
        assertEquals(nn.getNNType(), NetworkType.LSTM);
        assertEquals(nn.getLayers().get(0).getLayerType(), LayerType.InputCell);
        assertEquals(nn.getLayers().get(1).getLayerType(), LayerType.MemoryCell);
        assertEquals(nn.getLayers().get(2).getLayerType(), LayerType.MemoryCell);
        assertEquals(nn.getLayers().get(3).getLayerType(), LayerType.OutputCell);


        // ACTIVATION FUNCTION
        for (int i = 0; i < 4; i++) {
            assertEquals(nn.getLayers().get(i).getActivationFunction(), ActivationFunction.None);
        }

        // SIZE
        assertEquals(nn.getLayers().size(), nn.getDefaultNumberOfLayers());

        // NUMBER OF NEURONS
        assertEquals(nn.getLayers().get(0).getNeurons(), 3);
        assertEquals(nn.getLayers().get(1).getNeurons(), 3);
        assertEquals(nn.getLayers().get(2).getNeurons(), 3);
        assertEquals(nn.getLayers().get(3).getNeurons(), 3);
    }

    @Test
    public void buildLongTermMemoryNNTest() throws Exception {
        LongShortTermMemoryNN nn = new LongShortTermMemoryNN();

        // TYPE
        assertEquals(nn.getNNType(), NetworkType.LSTM);

        // SIZE
        assertEquals(nn.getLayers().size(), 0);

        // NUMBER OF NEURONS
        assertEquals(nn.getDefaultNumberOfLayers(), 4);

    }

}
