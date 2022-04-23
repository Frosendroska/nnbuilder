package org.hse.nnbuilder.nn.store;

import static org.assertj.core.api.Assertions.assertThat;

import org.hse.nnbuilder.nn.ConvolutionalNN;
import org.hse.nnbuilder.nn.FastForwardNN;
import org.hse.nnbuilder.nn.LongTermMemoryNN;
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
        FastForwardNN nn = FastForwardNN.buildDefaultFastForwardNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(2);
    }

    @Test
    void testLargeNN() {
        FastForwardNN nn = FastForwardNN.buildDefaultFastForwardNN();
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
        LongTermMemoryNN nn = LongTermMemoryNN.buildDefaultLongTermMemoryNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(4);
    }
}
