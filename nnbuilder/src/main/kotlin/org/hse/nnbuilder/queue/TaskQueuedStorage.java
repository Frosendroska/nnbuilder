package org.hse.nnbuilder.queue;

import javax.transaction.Transactional;
import org.hse.nnbuilder.dataset.DatasetRepository;
import org.hse.nnbuilder.dataset.DatasetStored;
import org.hse.nnbuilder.nn.store.NeuralNetworkRepository;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.services.errors.NotFoundError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Component;

@Component
public class TaskQueuedStorage {

    @Autowired
    private TasksQueueRepository tasksQueueRepository;

    @Autowired
    private NeuralNetworkRepository neuralNetworkRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    TaskQueuedStorage() {}

    public TaskQueued getByIdOrThrow(Long id) {
        try {
            return tasksQueueRepository.getById(id);
        } catch (ObjectRetrievalFailureException _e) {
            throw new NotFoundError(id, "task");
        }
    }

    @Transactional
    public void saveTaskQueuedTransition(TaskQueued taskQueued, DatasetStored dsStored, NeuralNetworkStored nnStored) {

        dsStored.getTasks().add(taskQueued);
        nnStored.getTasks().add(taskQueued);

        neuralNetworkRepository.save(nnStored);
        datasetRepository.save(dsStored);
        tasksQueueRepository.save(taskQueued);
    }
}
