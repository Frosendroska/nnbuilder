package org.hse.nnbuilder.queue;

import org.hse.nnbuilder.nn.FeedForwardNN;
import org.hse.nnbuilder.nn.store.NeuralNetworkRepository;
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
    private NeuralNetworkRepository neuralNetworkRepository;

    @Test
    void testAddEmptyTask() {
        TasksQueue tq = new TasksQueue();
        tasksQueueRepository.save(tq);
    }

    @Test
    void testAddLearningTask() {
        FeedForwardNN nn = FeedForwardNN.buildDefaultFastForwardNN();
        NeuralNetworkStored nnStored = new NeuralNetworkStored(nn);
        neuralNetworkRepository.save(nnStored);

        TasksQueue tq = new TasksQueue(
                TaskType.TrainNN, nnStored.getNnId(), 12L /*TODO вообще нужно сделать join в табличку с задачами*/);
        tasksQueueRepository.save(tq);
    }
}
