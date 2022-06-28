package org.hse.nnbuilder.nn.store;

import org.hse.nnbuilder.services.errors.NotFoundError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Component;

@Component
public class NeuralNetworkStorage {

    @Autowired
    private NeuralNetworkRepository neuralNetworkRepository;

    public NeuralNetworkStored getByIdOrThrow(Long id) {
        try {
            return neuralNetworkRepository.getById(id);
        } catch (ObjectRetrievalFailureException _e) {
            throw new NotFoundError(id, "neural network");
        }
    }

    public NeuralNetworkStored getByGeneralNNIdOrThrow(Long id) {
        try {
            return neuralNetworkRepository.getById(id);
        } catch (ObjectRetrievalFailureException _e) {
            throw new NotFoundError(id, "neural network");
        }
    }

    public void save(NeuralNetworkStored neuralNetworkStored) {
        neuralNetworkRepository.save(neuralNetworkStored);
    }
}
