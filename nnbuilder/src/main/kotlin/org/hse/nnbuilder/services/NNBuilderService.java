package org.hse.nnbuilder.services;

import static org.hse.nnbuilder.services.NNBuilderServicesGrpc.getNnbuilderMethod;

import net.devh.boot.grpc.server.service.GrpcService;
import org.hse.nnbuilder.nn.ConvolutionalNN;
import org.hse.nnbuilder.nn.FeedForwardNN;
import org.hse.nnbuilder.nn.LongShortTermMemoryNN;
import org.hse.nnbuilder.nn.RecurrentNN;
import org.hse.nnbuilder.nn.store.*;

import io.grpc.stub.StreamObserver;
import org.hse.nnbuilder.services.Nnbuilder.NNBuildingResponse;
import org.hse.nnbuilder.services.Nnmodification.NetworkType;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class NNBuilderService extends NNBuilderServicesGrpc.NNBuilderServicesImplBase {
    @Autowired
    private NeuralNetworkRepository neuralNetworkRepository;

    @Override
    public void nnbuilder(Nnbuilder.NNBuildingRequest request,
                          StreamObserver<NNBuildingResponse> responseObserver) {

        io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNnbuilderMethod(), responseObserver);

        long nnId = 0;

        if (request.getNnType() == NetworkType.FF) {
            FeedForwardNN ffnn = FeedForwardNN.buildDefaultFastForwardNN();
            NeuralNetworkStored nnStored = new NeuralNetworkStored(ffnn);
            neuralNetworkRepository.save(nnStored);
            nnId = nnStored.getId();

        } else if (request.getNnType() == NetworkType.RNN) {
            RecurrentNN rnn = RecurrentNN.buildDefaultRecurrentNN();
            NeuralNetworkStored nnStored = new NeuralNetworkStored(rnn);
            neuralNetworkRepository.save(nnStored);
            nnId = nnStored.getId();

        } else if (request.getNnType() == NetworkType.LSTM) {
            LongShortTermMemoryNN lstmnn = LongShortTermMemoryNN.buildDefaultLongTermMemoryNN();
            NeuralNetworkStored nnStored = new NeuralNetworkStored(lstmnn);
            neuralNetworkRepository.save(nnStored);
            nnId = nnStored.getId();

        } else if (request.getNnType() == NetworkType.CNN) {
            ConvolutionalNN cnn = ConvolutionalNN.buildDefaultConvolutionalNN();
            NeuralNetworkStored nnStored = new NeuralNetworkStored(cnn);
            neuralNetworkRepository.save(nnStored);
            nnId = nnStored.getId();

        }

        NNBuildingResponse responseWithOk = NNBuildingResponse
                .newBuilder()
                .setNnId(nnId)
                .build();
        responseObserver.onNext(responseWithOk);
        responseObserver.onCompleted();
    }
}
