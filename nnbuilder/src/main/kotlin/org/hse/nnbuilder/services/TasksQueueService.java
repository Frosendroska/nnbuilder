// package org.hse.nnbuilder.services;
//
// import com.google.protobuf.Timestamp;
// import io.grpc.stub.StreamObserver;
// import net.devh.boot.grpc.server.service.GrpcService;
// import org.hse.nnbuilder.nn.store.NeuralNetworkRepository;
// import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
// import org.hse.nnbuilder.queue.TaskQueued;
// import org.hse.nnbuilder.queue.TasksQueueRepository;
// import org.hse.nnbuilder.services.Tasksqueue.GettingInformationRequest;
// import org.hse.nnbuilder.services.Tasksqueue.GettingInformationResponse;
// import org.hse.nnbuilder.services.Tasksqueue.TaskCreationRequest;
// import org.hse.nnbuilder.services.Tasksqueue.TaskCreationResponse;
// import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;
// import org.hse.nnbuilder.services.Tasksqueue.TaskType;
// import org.springframework.beans.factory.annotation.Autowired;
//
// @GrpcService
// public class TasksQueueService extends TasksQueueServiceGrpc.TasksQueueServiceImplBase {
//
//     @Autowired
//     private TasksQueueRepository tasksQueueRepository;
//
//     @Autowired
//     private NeuralNetworkRepository neuralNetworkRepository;
//
//     @Override
//     public void createtask(
//             TaskCreationRequest request,
//             StreamObserver<TaskCreationResponse> responseObserver) {
//
//         // Get data from request
//         TaskType name = request.getName();
//         long nnId = request.getNnId();
//         long dataId = request.getDataId();
//
//         // Make a task in DB
//         TaskQueued tq = new TaskQueued(name, neuralNetworkRepository.getById(nnId), dataId);
//         tasksQueueRepository.save(tq);
//
//         // Response with id on task
//         long taskId = tq.getTaskId();
//         TaskCreationResponse responseWithTaskId = TaskCreationResponse.newBuilder().setTaskId(taskId).build();
//         responseObserver.onNext(responseWithTaskId);
//         responseObserver.onCompleted();
//     }
//
//     @Override
//     public void getinformation(
//             GettingInformationRequest request,
//             StreamObserver<GettingInformationResponse> responseObserver) {
//
//         // Get data and task from request
//         long taskId = request.getTaskId();
//         TaskQueued tq = tasksQueueRepository.getById(taskId);
//
//         // Response with info of task
//         TaskType taskName = tq.getTaskName();
//         NeuralNetworkStored nnId = tq.getNeuralNetworkStored();
//         long dataId = tq.getDataId();
//         Timestamp startTime = tq.getStartTime();
//         TaskStatus taskStatus = tq.getTaskStatus();
//         GettingInformationResponse responseWithInfo =
//                 GettingInformationResponse.newBuilder().setTaskName(taskName)
//                         // .setNnId(nnId)
//                         .setDataId(dataId)
//                         .setStartTime(startTime)
//                         .setTaskStatus(taskStatus).build();
//         responseObserver.onNext(responseWithInfo);
//         responseObserver.onCompleted();
//     }
// }
