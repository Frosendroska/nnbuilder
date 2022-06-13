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

    final static Long DEFAULT_EPOCH_NUMBER = 100L;

    @Test
    void testAddEmptyTask() {
        TaskQueued tq = new TaskQueued();
        tasksQueueRepository.save(tq);
    }

    @Test
    void testAddLearningTaskFFNN() throws IOException {
        // Neural Network
        FeedForwardNN nn = FeedForwardNN.buildDefaultFastForwardNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);

        DatasetStored dsStored = new DatasetStored(DatasetUtil.readDatasetFile(), null);

        // Task
        TaskQueued taskQueued = new TaskQueued(TaskType.ApplyToData, nnStored, dsStored, DEFAULT_EPOCH_NUMBER);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued, dsStored, nnStored);
    }

    @Test
    void testAddLearningTaskCNN() throws IOException {
        ConvolutionalNN nn = ConvolutionalNN.buildDefaultConvolutionalNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);

        DatasetStored dsStored = new DatasetStored(DatasetUtil.readDatasetFile(), "industry");

        // Task
        TaskQueued taskQueued =
                new TaskQueued(TaskType.TrainNNClassification, nnStored, dsStored, DEFAULT_EPOCH_NUMBER);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued, dsStored, nnStored);
    }

    @Test
    void testAddLearningTasks() throws IOException {
        LongShortTermMemoryNN lstmnn = LongShortTermMemoryNN.buildDefaultLongTermMemoryNN();
        NeuralNetworkStored lstmStored = new NeuralNetworkStored(lstmnn);

        DatasetStored dsStoredTrain = new DatasetStored(DatasetUtil.readDatasetFile(), "industry");

        // Task
        TaskQueued taskQueued1 =
                new TaskQueued(TaskType.TrainNNRegression, lstmStored, dsStoredTrain, DEFAULT_EPOCH_NUMBER);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued1, dsStoredTrain, lstmStored);

        RecurrentNN rnn = RecurrentNN.buildDefaultRecurrentNN();
        NeuralNetworkStored rnnStored = new NeuralNetworkStored(rnn);

        DatasetStored dsStoredApply = new DatasetStored(DatasetUtil.readDatasetFile(), "industry");

        // Task
        TaskQueued taskQueued2 = new TaskQueued(TaskType.ApplyToData, rnnStored, dsStoredApply, DEFAULT_EPOCH_NUMBER);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued2, dsStoredApply, rnnStored);
    }
}
