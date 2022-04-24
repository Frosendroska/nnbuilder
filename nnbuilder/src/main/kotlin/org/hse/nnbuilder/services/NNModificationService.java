package org.hse.nnbuilder.services;

import net.devh.boot.grpc.server.service.GrpcService;
import org.hse.nnbuilder.nn.store.*;

import static org.hse.nnbuilder.services.NNModificationServicesGrpc.getNnmodificationMethod;

import io.grpc.stub.StreamObserver;
import org.hse.nnbuilder.services.Nnmodification.NNModificationResponse;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class NNModificationService extends NNModificationServicesGrpc.NNModificationServicesImplBase {

    @Autowired
    private NeuralNetworkRepository neuralNetworkRepository;

    @Override
    public void nnmodification(Nnmodification.NNModificationRequest request,
                               StreamObserver<NNModificationResponse> responseObserver) {

        io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNnmodificationMethod(), responseObserver);

        try {
            Long nnId = request.getNnId();
            if (request.hasAddLayer()) {
                NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
                loaded.getNeuralNetwork().addLayer(
                        request.getAddLayer().getIndex(), // i
                        request.getAddLayer().getLType() // lType
                );
            }
            if (request.hasDelLayer()) {
                NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
                loaded.getNeuralNetwork().delLayer(
                        request.getDelLayer().getIndex() // i
                );
            }
            if (request.hasChangeActivationFunction()) {
                NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
                loaded.getNeuralNetwork().changeActivationFunction(
                        request.getChangeActivationFunction().getIndex(), // i
                        request.getChangeActivationFunction().getF() // f
                );
            }
            if (request.hasChangeNumberOfNeuron()) {
                NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
                loaded.getNeuralNetwork().changeNumberOfNeuron(
                        request.getChangeNumberOfNeuron().getIndex(), // i
                        request.getChangeNumberOfNeuron().getNumber() // n
                );
            }
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
                .build();
        responseObserver.onNext(responseWithOk);
        responseObserver.onCompleted();
    }
}
