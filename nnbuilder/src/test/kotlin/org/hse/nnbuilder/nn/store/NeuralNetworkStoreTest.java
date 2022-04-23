package org.hse.nnbuilder.nn.store;

import static org.assertj.core.api.Assertions.assertThat;

import org.hse.nnbuilder.nn.ConvolutionalNN;
import org.hse.nnbuilder.nn.FeedForwardNN;
import org.hse.nnbuilder.nn.LongShortTermMemoryNN;
import org.hse.nnbuilder.nn.RecurrentNN;
import org.hse.nnbuilder.services.Nnmodification.LayerType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class NeuralNetworkStoreTest {

    @Autowired
    private NeuralNetworkRepository neuralNetworkRepository;

    @Test
    void testFF() {
        FeedForwardNN nn = FeedForwardNN.buildDefaultFastForwardNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(2);
    }

    @Test
    void testLargeNN() {
        FeedForwardNN nn = FeedForwardNN.buildDefaultFastForwardNN();
        for (int i = 0; i != 100; ++i) {
            nn.addLayer(1, LayerType.HiddenCell);
        }

        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(102);
    }

    @Test
    void testCNN() {
        ConvolutionalNN nn = ConvolutionalNN.buildDefaultConvolutionalNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(8);
    }

    @Test
    void testRNN() {
        RecurrentNN nn = RecurrentNN.buildDefaultRecurrentNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(4);
    }

    @Test
    void testLSTM() {
        LongShortTermMemoryNN nn = LongShortTermMemoryNN.buildDefaultLongTermMemoryNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(4);
    }
}
