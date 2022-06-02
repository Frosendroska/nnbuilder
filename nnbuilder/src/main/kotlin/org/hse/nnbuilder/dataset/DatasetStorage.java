package org.hse.nnbuilder.dataset;

import org.hse.nnbuilder.services.errors.NotFoundError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Component;

@Component
public class DatasetStorage {

    @Autowired
    private DatasetRepository datasetRepository;

    public DatasetStored getByIdOrThrow(Long id) {
        try {
            return datasetRepository.getById(id);
        } catch (ObjectRetrievalFailureException _e) {
            throw new NotFoundError(id, "dataset");
        }
    }

    public void save(DatasetStored datasetStored) {
        datasetRepository.save(datasetStored);
    }
}
