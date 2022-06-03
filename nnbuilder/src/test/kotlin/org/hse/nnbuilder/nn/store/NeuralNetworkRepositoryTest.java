package org.hse.nnbuilder.nn.store;

import static org.assertj.core.api.Assertions.assertThat;

import org.hse.nnbuilder.nn.ConvolutionalNN;
import org.hse.nnbuilder.nn.FeedForwardNN;
import org.hse.nnbuilder.nn.LongShortTermMemoryNN;
import org.hse.nnbuilder.nn.RecurrentNN;
import org.hse.nnbuilder.services.Nnmodification.LayerType;
import org.hse.nnbuilder.user.User;
import org.hse.nnbuilder.user.UserService;
import org.hse.nnbuilder.version_controller.GeneralNeuralNetwork;
import org.hse.nnbuilder.version_controller.GeneralNeuralNetworkService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class NeuralNetworkRepositoryTest {

    @Autowired
    private UserService userService;

    @Autowired
    private NeuralNetworkRepository neuralNetworkRepository;

    @Autowired
    private GeneralNeuralNetworkService generalNeuralNetworkService;

    private GeneralNeuralNetwork testingGeneralNeuralNetwork;

    @BeforeAll
    void prepare() {
        User user = userService.save("Ivan", "ivan@gmail.com", "password");
        testingGeneralNeuralNetwork = generalNeuralNetworkService.create(user);
    }

    @Test
    void testFF() {
        FeedForwardNN nn = FeedForwardNN.buildDefaultFastForwardNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn, testingGeneralNeuralNetwork);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getNnId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(2);
    }

    @Test
    void testLargeNN() {
        FeedForwardNN nn = FeedForwardNN.buildDefaultFastForwardNN();
        for (int i = 0; i != 100; ++i) {
            nn.addLayer(1, LayerType.HiddenCell);
        }

        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn, testingGeneralNeuralNetwork);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getNnId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(102);
    }

    @Test
    void testCNN() {
        ConvolutionalNN nn = ConvolutionalNN.buildDefaultConvolutionalNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn, testingGeneralNeuralNetwork);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getNnId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(8);
    }

    @Test
    void testRNN() {
        RecurrentNN nn = RecurrentNN.buildDefaultRecurrentNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn, testingGeneralNeuralNetwork);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getNnId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(4);
    }

    @Test
    void testLSTM() {
        LongShortTermMemoryNN nn = LongShortTermMemoryNN.buildDefaultLongTermMemoryNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn, testingGeneralNeuralNetwork);
        neuralNetworkRepository.save(nnStored);

        NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnStored.getNnId());

        assertThat(loaded.getNeuralNetwork().getLayers()).hasSize(4);
    }
}
