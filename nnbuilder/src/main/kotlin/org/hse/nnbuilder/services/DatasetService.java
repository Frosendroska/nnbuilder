package org.hse.nnbuilder.services;

import io.grpc.stub.StreamObserver;
import org.hse.nnbuilder.dataset.DatasetStorage;
import org.hse.nnbuilder.dataset.DatasetStored;
import org.hse.nnbuilder.services.Dataset.UploadDatasetRequest;
import org.hse.nnbuilder.services.Dataset.UploadDatasetResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class DatasetService extends DatasetServiceGrpc.DatasetServiceImplBase {

    @Autowired
    private DatasetStorage datasetStorage;

    @Override
    public void uploadDataset(UploadDatasetRequest request, StreamObserver<UploadDatasetResponse> responseObserver) {

        // Get data from request
        byte[] content = request.getContent().toByteArray();
        String targetColumnName = request.getTargetColumnName();

        // Load dataset in DB
        DatasetStored dsStored = new DatasetStored(content, targetColumnName.isEmpty() ? null : targetColumnName);
        datasetStorage.save(dsStored);

        // Response
        long dsId = dsStored.getDatasetId();
        UploadDatasetResponse responseWithDSId =
                UploadDatasetResponse.newBuilder().setDatasetId(dsId).build();
        responseObserver.onNext(responseWithDSId);
        responseObserver.onCompleted();
    }
}
