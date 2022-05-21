// package org.hse.nnbuilder.services;
//
// import io.grpc.stub.StreamObserver;
// import net.devh.boot.grpc.server.service.GrpcService;
// import org.hse.nnbuilder.nn.ConvolutionalNN;
// import org.hse.nnbuilder.nn.FeedForwardNN;
// import org.hse.nnbuilder.nn.LongShortTermMemoryNN;
// import org.hse.nnbuilder.nn.RecurrentNN;
// import org.hse.nnbuilder.nn.store.NeuralNetworkRepository;
// import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
// import org.hse.nnbuilder.services.Nnmodification.NNCreationRequest;
// import org.hse.nnbuilder.services.Nnmodification.NNCreationResponse;
// import org.hse.nnbuilder.services.Nnmodification.NNModificationRequest;
// import org.hse.nnbuilder.services.Nnmodification.NNModificationResponse;
// import org.hse.nnbuilder.services.Nnmodification.NetworkType;
// import org.springframework.beans.factory.annotation.Autowired;
//
// @GrpcService
// public class NNModificationService extends NNModificationServiceGrpc.NNModificationServiceImplBase {
//
//     @Autowired
//     private NeuralNetworkRepository neuralNetworkRepository;
//
//     @Override
//     public void modifynn(
//             NNModificationRequest request, StreamObserver<NNModificationResponse> responseObserver) {
//
//         Long nnId = request.getNnId();
//         if (request.hasAddLayer()) {
//             NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
//             loaded.getNeuralNetwork()
//                     .addLayer(
//                             request.getAddLayer().getIndex(), // i
//                             request.getAddLayer().getLType() // lType
//                     );
//         }
//         if (request.hasDelLayer()) {
//             NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
//             loaded.getNeuralNetwork()
//                     .delLayer(
//                             request.getDelLayer().getIndex() // i
//                     );
//         }
//         if (request.hasChangeActivationFunction()) {
//             NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
//             loaded.getNeuralNetwork()
//                     .changeActivationFunction(
//                             request.getChangeActivationFunction().getIndex(), // i
//                             request.getChangeActivationFunction().getF() // f
//                     );
//         }
//         if (request.hasChangeNumberOfNeuron()) {
//             NeuralNetworkStored loaded = neuralNetworkRepository.getById(nnId);
//             loaded.getNeuralNetwork()
//                     .changeNumberOfNeuron(
//                             request.getChangeNumberOfNeuron().getIndex(), // i
//                             request.getChangeNumberOfNeuron().getNumber() // n
//                     );
//         }
//
//         NNModificationResponse responseWithOk =
//                 NNModificationResponse.newBuilder().build();
//         responseObserver.onNext(responseWithOk);
//         responseObserver.onCompleted();
//     }
//
//     @Override
//     public void createnn(
//             NNCreationRequest request,
//             StreamObserver<Nnmodification.NNCreationResponse> responseObserver) {
//
//         long nnId = 0;
//
//         if (request.getNnType() == NetworkType.FF) {
//             FeedForwardNN ffnn = FeedForwardNN.buildDefaultFastForwardNN();
//             NeuralNetworkStored nnStored = new NeuralNetworkStored(ffnn);
//             neuralNetworkRepository.save(nnStored);
//             nnId = nnStored.getNnId();
//         } else if (request.getNnType() == NetworkType.RNN) {
//             RecurrentNN rnn = RecurrentNN.buildDefaultRecurrentNN();
//             NeuralNetworkStored nnStored = new NeuralNetworkStored(rnn);
//             neuralNetworkRepository.save(nnStored);
//             nnId = nnStored.getNnId();
//         } else if (request.getNnType() == NetworkType.LSTM) {
//             LongShortTermMemoryNN lstmnn = LongShortTermMemoryNN.buildDefaultLongTermMemoryNN();
//             NeuralNetworkStored nnStored = new NeuralNetworkStored(lstmnn);
//             neuralNetworkRepository.save(nnStored);
//             nnId = nnStored.getNnId();
//         } else if (request.getNnType() == NetworkType.CNN) {
//             ConvolutionalNN cnn = ConvolutionalNN.buildDefaultConvolutionalNN();
//             NeuralNetworkStored nnStored = new NeuralNetworkStored(cnn);
//             neuralNetworkRepository.save(nnStored);
//             nnId = nnStored.getNnId();
//         }
//
//         NNCreationResponse responseWithNNId =
//                 NNCreationResponse.newBuilder().setNnId(nnId).build();
//         responseObserver.onNext(responseWithNNId);
//         responseObserver.onCompleted();
//     }
// }
