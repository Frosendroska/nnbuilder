package org.hse.nnbuilder.services;

import static org.hse.nnbuilder.services.NNModificationServicesGrpc.getNnmodificationMethod;

import io.grpc.stub.StreamObserver;
import org.hse.nnbuilder.services.Nnmodification.NNModificationResponse;

public class NNBuilderService extends NNModificationServicesGrpc.NNModificationServicesImplBase {

    @Override
    public void nnmodification(Nnmodification.NNModificationRequest request,
                               StreamObserver<NNModificationResponse> responseObserver) {
        io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNnmodificationMethod(), responseObserver);

        if (request.hasAddLayer()) {

        }
    }
}
