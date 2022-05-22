package org.hse.nnbuilder.services;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hse.nnbuilder.dataset.DatasetRepository;
import org.hse.nnbuilder.dataset.DatasetStored;
import org.hse.nnbuilder.nn.store.NeuralNetworkRepository;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.queue.TaskQueued;
import org.hse.nnbuilder.queue.TaskQueuedStorage;
import org.hse.nnbuilder.queue.TasksQueueRepository;
import org.hse.nnbuilder.services.Tasksqueue.GettingInformationRequest;
import org.hse.nnbuilder.services.Tasksqueue.GettingInformationResponse;
import org.hse.nnbuilder.services.Tasksqueue.TaskCreationRequest;
import org.hse.nnbuilder.services.Tasksqueue.TaskCreationResponse;
import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;
import org.hse.nnbuilder.services.Tasksqueue.TaskType;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class TasksQueueService extends TasksQueueServiceGrpc.TasksQueueServiceImplBase {

    @Autowired
    private TasksQueueRepository tasksQueueRepository;

    @Autowired
    private NeuralNetworkRepository neuralNetworkRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private TaskQueuedStorage taskQueuedStorage;

    @Override
    public void createtask(
            TaskCreationRequest request,
            StreamObserver<TaskCreationResponse> responseObserver) {

        // Get data from request
        TaskType name = request.getName();
        NeuralNetworkStored nnStored = neuralNetworkRepository.getById(request.getNnId());
        DatasetStored dsStored = datasetRepository.getById(request.getDataId());

        // Make a task in DB
        TaskQueued taskQueued = new TaskQueued(name, nnStored, dsStored);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued, dsStored, nnStored);

        // Response with id on task
        long taskId = taskQueued.getTaskId();
        TaskCreationResponse responseWithTaskId = TaskCreationResponse.newBuilder().setTaskId(taskId).build();
        responseObserver.onNext(responseWithTaskId);
        responseObserver.onCompleted();
    }

    @Override
    public void getinformation(
            GettingInformationRequest request,
            StreamObserver<GettingInformationResponse> responseObserver) {

        // Get data and task from request
        long taskId = request.getTaskId();
        TaskQueued tq = tasksQueueRepository.getById(taskId);

        // Response with info of task
        TaskStatus taskStatus = tq.getTaskStatus();
        Timestamp startTaskTime = tq.getStartTaskTime();
        Instant now = Instant.now();
        Timestamp curTime = Timestamp.newBuilder().setSeconds(now.getEpochSecond()).setNanos(now.getNano()).build();

        GettingInformationResponse responseWithInfo = GettingInformationResponse.newBuilder()
                .setTimeDeltaSeconds(
                        taskStatus == TaskStatus.HaveNotStarted
                                ? 0
                                : curTime.getSeconds() - startTaskTime.getSeconds()
                )
                .setTaskStatus(taskStatus).build();
        responseObserver.onNext(responseWithInfo);
        responseObserver.onCompleted();
    }
}
