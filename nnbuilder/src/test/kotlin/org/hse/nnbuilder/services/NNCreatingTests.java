package org.hse.nnbuilder.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.hse.nnbuilder.services.Layer.LayerType;

public class NNCreatingTests {

    @Test
    public void buildDefaultFastForwardNNTest() throws Exception {
        FastForwardNN nn = FastForwardNN.buildDefaultFastForwardNN();

        // TYPE
        assertEquals(nn.getLayers().get(0).getLayerType(), Layer.LayerType.InputCell);
        assertEquals(nn.getLayers().get(1).getLayerType(), LayerType.OutputCell);

        // ACTIVATION FUNCTION
        assertEquals(nn.getLayers().get(0).getActivationFunction(), Layer.ActivationFunction.None);
        assertEquals(nn.getLayers().get(1).getActivationFunction(), Layer.ActivationFunction.None);

        // SIZE
        assertEquals(nn.getLayers().size(), nn.getDefaultNumberOfLayers());

        // NUMBER OF NEURONS
        assertEquals(nn.getLayers().get(0).getNeurons(), 2);
        assertEquals(nn.getLayers().get(1).getNeurons(), 1);
    }
}
