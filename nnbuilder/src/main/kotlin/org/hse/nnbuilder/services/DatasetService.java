package org.hse.nnbuilder.services;

import io.grpc.stub.StreamObserver;
import org.hse.nnbuilder.dataset.DatasetStorage;
import org.hse.nnbuilder.dataset.DatasetStored;
import org.hse.nnbuilder.services.Dataset.LoadDatasetRequest;
import org.hse.nnbuilder.services.Dataset.LoadDatasetResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class DatasetService extends DatasetServiceGrpc.DatasetServiceImplBase {

    @Autowired
    private DatasetStorage datasetStorage;

    @Override
    public void loadDataset(LoadDatasetRequest request, StreamObserver<LoadDatasetResponse> responseObserver) {

        // Get data from request
        byte[] content = request.getContent().toByteArray();
        String targetColumnName = request.getTargetColumnName();

        // Load dataset in DB
        DatasetStored dsStored = new DatasetStored(
                content,
                targetColumnName.isEmpty() ? null : targetColumnName
        );
        datasetStorage.save(dsStored);

        // Response
        long dsId = dsStored.getDsId();
        LoadDatasetResponse responseWithDSId =
                LoadDatasetResponse.newBuilder().setDsId(dsId).build();
        responseObserver.onNext(responseWithDSId);
        responseObserver.onCompleted();
    }
}
