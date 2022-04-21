package org.hse.nnbuilder.services;

import static org.hse.nnbuilder.services.NNModificationServicesGrpc.getNnmodificationMethod;

import io.grpc.stub.StreamObserver;
import org.hse.nnbuilder.services.Nnmodification.NNModificationResponse;

public class NNModificationServices extends NNModificationServicesGrpc.NNModificationServicesImplBase {

    @Override
    public void nnmodification(Nnmodification.NNModificationRequest request,
                               StreamObserver<NNModificationResponse> responseObserver) {
        io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNnmodificationMethod(), responseObserver);

        try {
            long nnId = request.getNnId();
            if (request.hasAddLayer()) {
                // получить нейронку из БД
                // вызвать на ней добавление слоя
            }
            if (request.hasDelLayer()) {


            }
            if (request.hasChangeActivationFunction()) {


            }
            if (request.hasChangeNumberOfNeuron()) {

            }
            // TODO кастомное исключение
        } catch (IllegalArgumentException e) {
            NNModificationResponse responseWithError = NNModificationResponse
                    .newBuilder()
                    .setException(e.toString())
                    .build();
            responseObserver.onNext(responseWithError);
            responseObserver.onCompleted();
        }
        NNModificationResponse responseWithOk = NNModificationResponse
                .newBuilder()
                .setException("")
                .build();
        responseObserver.onNext(responseWithOk);
        responseObserver.onCompleted();
    }
}
