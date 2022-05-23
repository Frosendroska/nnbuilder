package org.hse.nnbuilder.queue;

import java.io.IOException;
import org.hse.nnbuilder.DatasetUtil;
import org.hse.nnbuilder.dataset.DatasetStored;
import org.hse.nnbuilder.nn.FeedForwardNN;
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

        DatasetStored dsStored = new DatasetStored(DatasetUtil.readDatasetFile());

        // Task
        TaskQueued taskQueued = new TaskQueued(TaskType.TrainNNClassification, nnStored, dsStored);

        // TaskQueuedStorage tqs = new TaskQueuedStorage();
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued, dsStored, nnStored);
    }

    // @Test
    // void testAddLearningTaskCNN() {
    //     ConvolutionalNN nn = ConvolutionalNN.buildDefaultConvolutionalNN();
    //     NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);
    //     neuralNetworkRepository.save(nnStored);
    //
    //     QueuedTask tq = new QueuedTask(
    //             TaskType.TrainNN, nnStored, 2L /*TODO вообще нужно айди датасета*/);
    //     tasksQueueRepository.save(tq);
    // }
    //
    // @Test
    // void testAddLearningTasks() {
    //     ConvolutionalNN cnn = ConvolutionalNN.buildDefaultConvolutionalNN();
    //     NeuralNetworkStored cnnStored = new NeuralNetworkStored(cnn);
    //     neuralNetworkRepository.save(cnnStored);
    //
    //     QueuedTask ctq = new QueuedTask(
    //             TaskType.TrainNN, cnnStored, 2L /*TODO вообще нужно айди датасета*/);
    //     tasksQueueRepository.save(ctq);
    //
    //     FeedForwardNN ffnn = FeedForwardNN.buildDefaultFastForwardNN();
    //     NeuralNetworkStored ffnnStored = new NeuralNetworkStored(ffnn);
    //     neuralNetworkRepository.save(ffnnStored);
    //
    //     QueuedTask fftq = new QueuedTask(
    //             TaskType.TrainNN, ffnnStored, 1L /*TODO вообще нужно айди датасета*/);
    //     tasksQueueRepository.save(fftq);
    // }
}
