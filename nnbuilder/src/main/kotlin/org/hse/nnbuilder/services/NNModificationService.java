package org.hse.nnbuilder.services;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hse.nnbuilder.nn.ConvolutionalNN;
import org.hse.nnbuilder.nn.FeedForwardNN;
import org.hse.nnbuilder.nn.LongShortTermMemoryNN;
import org.hse.nnbuilder.nn.RecurrentNN;
import org.hse.nnbuilder.nn.store.NeuralNetworkRepository;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.services.Nnmodification.NNCreationResponse;
import org.hse.nnbuilder.services.Nnmodification.NNModificationResponse;
import org.hse.nnbuilder.services.Nnmodification.NetworkType;
import org.hse.nnbuilder.user.User;
import org.hse.nnbuilder.user.UserService;
import org.hse.nnbuilder.version_controller.GeneralNeuralNetwork;
import org.hse.nnbuilder.version_controller.GeneralNeuralNetworkService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class NNModificationService extends NNModificationServiceGrpc.NNModificationServiceImplBase {

    @Autowired
    private NeuralNetworkRepository neuralNetworkRepository;

    @Autowired
    private GeneralNeuralNetworkService generalNeuralNetworkService;

    @Autowired
    private UserService userService;

    @Override
    public void modifynn(
            Nnmodification.NNModificationRequest request, StreamObserver<NNModificationResponse> responseObserver) {

        Long nnId = request.getNnId();
        if (request.hasAddLayer()) {
            NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
            loaded.getNeuralNetwork()
                    .addLayer(
                            request.getAddLayer().getIndex(), // i
                            request.getAddLayer().getLType() // lType
                            );
        }
        if (request.hasDelLayer()) {
            NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
            loaded.getNeuralNetwork()
                    .delLayer(
                            request.getDelLayer().getIndex() // i
                            );
        }
        if (request.hasChangeActivationFunction()) {
            NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
            loaded.getNeuralNetwork()
                    .changeActivationFunction(
                            request.getChangeActivationFunction().getIndex(), // i
                            request.getChangeActivationFunction().getF() // f
                            );
        }
        if (request.hasChangeNumberOfNeuron()) {
            NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
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

    // @Secured("ROLE_USER")
    @Override
    public void createnn(
            Nnmodification.NNCreationRequest request,
            StreamObserver<Nnmodification.NNCreationResponse> responseObserver) {
        // String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        String userEmail = "c";
        User user = userService.findByEmail(userEmail);

        long nnId = 0;

        // creating new project
        GeneralNeuralNetwork generalNeuralNetwork = generalNeuralNetworkService.create(user);

        if (request.getNnType() == NetworkType.FF) {
            FeedForwardNN ffnn = FeedForwardNN.buildDefaultFastForwardNN();
            NeuralNetworkStored nnStored = new NeuralNetworkStored(ffnn, generalNeuralNetwork);
            neuralNetworkRepository.save(nnStored);
            nnId = nnStored.getId();
            // System.out.println("NeuralNetworkStored in general..:");
            // GeneralNeuralNetwork temp = generalNeuralNetworkService.getById(generalNeuralNetwork.getId());
            // if(temp.getNNVersions() != null && !temp.getNNVersions().isEmpty()) {
            //     for (NeuralNetworkStored i : temp.getNNVersions()) {
            //         System.out.println(i.getId());
            //     }
            // }
            // System.out.println("General in new stored: " + nnStored.generalNeuralNetwork.getId());

        } else if (request.getNnType() == NetworkType.RNN) {
            RecurrentNN rnn = RecurrentNN.buildDefaultRecurrentNN();
            NeuralNetworkStored nnStored = new NeuralNetworkStored(rnn, generalNeuralNetwork);
            neuralNetworkRepository.save(nnStored);
            nnId = nnStored.getId();
        } else if (request.getNnType() == NetworkType.LSTM) {
            LongShortTermMemoryNN lstmnn = LongShortTermMemoryNN.buildDefaultLongTermMemoryNN();
            NeuralNetworkStored nnStored = new NeuralNetworkStored(lstmnn, generalNeuralNetwork);
            neuralNetworkRepository.save(nnStored);
            nnId = nnStored.getId();
        } else if (request.getNnType() == NetworkType.CNN) {
            ConvolutionalNN cnn = ConvolutionalNN.buildDefaultConvolutionalNN();
            NeuralNetworkStored nnStored = new NeuralNetworkStored(cnn, generalNeuralNetwork);
            neuralNetworkRepository.save(nnStored);
            nnId = nnStored.getId();
        }

        NNCreationResponse responseWithOk =
                NNCreationResponse.newBuilder().setNnId(nnId).build();
        responseObserver.onNext(responseWithOk);
        responseObserver.onCompleted();
    }
}
