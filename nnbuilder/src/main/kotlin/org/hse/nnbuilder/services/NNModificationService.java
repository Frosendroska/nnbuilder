package org.hse.nnbuilder.services;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hse.nnbuilder.nn.ConvolutionalNN;
import org.hse.nnbuilder.nn.FeedForwardNN;
import org.hse.nnbuilder.nn.LongShortTermMemoryNN;
import org.hse.nnbuilder.nn.RecurrentNN;
import org.hse.nnbuilder.nn.store.NeuralNetworkStorage;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.services.Nnmodification.*;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class NNModificationService extends NNModificationServiceGrpc.NNModificationServiceImplBase {

    @Autowired
    private NeuralNetworkStorage neuralNetworkStorage;

    @Override
    public void modifynn(NNModificationRequest request, StreamObserver<NNModificationResponse> responseObserver) {

        Long nnId = request.getNnId();
        NeuralNetworkStored loaded = neuralNetworkStorage.getByIdOrThrow(nnId);

        if (request.hasAddLayer()) {

            loaded.getNeuralNetwork()
                    .addLayer(
                            request.getAddLayer().getIndex(), // i
                            request.getAddLayer().getLType() // lType
                            );
        }
        if (request.hasDelLayer()) {
            loaded.getNeuralNetwork()
                    .delLayer(
                            request.getDelLayer().getIndex() // i
                            );
        }
        if (request.hasChangeActivationFunction()) {
            loaded.getNeuralNetwork()
                    .changeActivationFunction(
                            request.getChangeActivationFunction().getIndex(), // i
                            request.getChangeActivationFunction().getF() // f
                            );
        }
        if (request.hasChangeNumberOfNeuron()) {
            loaded.getNeuralNetwork()
                    .changeNumberOfNeuron(
                            request.getChangeNumberOfNeuron().getIndex(), // i
                            request.getChangeNumberOfNeuron().getNumber() // n
                            );
        }

        NNModificationResponse responseWithOk =
                NNModificationResponse.newBuilder().build();
        responseObserver.onNext(responseWithOk);
        responseObserver.onCompleted();
    }

    @Override
    public void createnn(
            NNCreationRequest request, StreamObserver<Nnmodification.NNCreationResponse> responseObserver) {

        NeuralNetworkStored nnStored;
        if (request.getNnType() == NetworkType.FF) {
            FeedForwardNN ffnn = FeedForwardNN.buildDefaultFastForwardNN();
            nnStored = new NeuralNetworkStored(ffnn);
        } else if (request.getNnType() == NetworkType.RNN) {
            RecurrentNN rnn = RecurrentNN.buildDefaultRecurrentNN();
            nnStored = new NeuralNetworkStored(rnn);
        } else if (request.getNnType() == NetworkType.LSTM) {
            LongShortTermMemoryNN lstmnn = LongShortTermMemoryNN.buildDefaultLongTermMemoryNN();
            nnStored = new NeuralNetworkStored(lstmnn);
        } else if (request.getNnType() == NetworkType.CNN) {
            ConvolutionalNN cnn = ConvolutionalNN.buildDefaultConvolutionalNN();
            nnStored = new NeuralNetworkStored(cnn);
        } else {
            throw new IllegalArgumentException(String.format(
                    "Unexpected neural network type %s", request.getNnType().name()));
        }

        neuralNetworkStorage.save(nnStored);
        NNCreationResponse responseWithNNId =
                NNCreationResponse.newBuilder().setNnId(nnStored.getNnId()).build();
        responseObserver.onNext(responseWithNNId);
        responseObserver.onCompleted();
    }
}
