package org.hse.nnbuilder.queue;

import java.io.IOException;
import org.hse.nnbuilder.DatasetUtil;
import org.hse.nnbuilder.dataset.DatasetStored;
import org.hse.nnbuilder.nn.ConvolutionalNN;
import org.hse.nnbuilder.nn.FeedForwardNN;
import org.hse.nnbuilder.nn.LongShortTermMemoryNN;
import org.hse.nnbuilder.nn.RecurrentNN;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.services.Tasksqueue.TaskType;
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
public class TasksQueueRepositoryTest {

    @Autowired
    private TasksQueueRepository tasksQueueRepository;

    @Autowired
    private TaskQueuedStorage taskQueuedStorage;

    @Autowired
    private UserService userService;

    @Autowired
    private GeneralNeuralNetworkService generalNeuralNetworkService;

    private GeneralNeuralNetwork testingGeneralNeuralNetwork;

    final Long defaultEpochAmount = 100L;

    @BeforeAll
    void prepare() {
        User user = userService.save("Ivan", "ivan@gmail.com", "password");
        testingGeneralNeuralNetwork = generalNeuralNetworkService.create(user);
    }

    @Test
    void testAddEmptyTask() {
        TaskQueued tq = new TaskQueued();
        tasksQueueRepository.save(tq);
    }

    @Test
    void testAddLearningTaskFFNN() throws IOException {
        // Neural Network
        FeedForwardNN nn = FeedForwardNN.buildDefaultFastForwardNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn, testingGeneralNeuralNetwork);

        DatasetStored dsStored = new DatasetStored(DatasetUtil.readDatasetFile(), null);

        // Task
        TaskQueued taskQueued = new TaskQueued(TaskType.ApplyToData, nnStored, dsStored, defaultEpochAmount);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued, dsStored, nnStored);
    }

    @Test
    void testAddLearningTaskCNN() throws IOException {
        ConvolutionalNN nn = ConvolutionalNN.buildDefaultConvolutionalNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn, testingGeneralNeuralNetwork);

        DatasetStored dsStored = new DatasetStored(DatasetUtil.readDatasetFile(), "industry");

        // Task
        TaskQueued taskQueued = new TaskQueued(TaskType.TrainNNClassification, nnStored, dsStored, defaultEpochAmount);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued, dsStored, nnStored);
    }

    @Test
    void testAddLearningTasks() throws IOException {
        LongShortTermMemoryNN lstmnn = LongShortTermMemoryNN.buildDefaultLongTermMemoryNN();
        NeuralNetworkStored lstmStored = new NeuralNetworkStored(lstmnn, testingGeneralNeuralNetwork);

        DatasetStored dsStoredTrain = new DatasetStored(DatasetUtil.readDatasetFile(), "industry");

        // Task
        TaskQueued taskQueued1 =
                new TaskQueued(TaskType.TrainNNRegression, lstmStored, dsStoredTrain, defaultEpochAmount);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued1, dsStoredTrain, lstmStored);

        RecurrentNN rnn = RecurrentNN.buildDefaultRecurrentNN();
        NeuralNetworkStored rnnStored = new NeuralNetworkStored(rnn, testingGeneralNeuralNetwork);

        DatasetStored dsStoredApply = new DatasetStored(DatasetUtil.readDatasetFile(), "industry");

        // Task
        TaskQueued taskQueued2 = new TaskQueued(TaskType.ApplyToData, rnnStored, dsStoredApply, defaultEpochAmount);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued2, dsStoredApply, rnnStored);
    }
}
