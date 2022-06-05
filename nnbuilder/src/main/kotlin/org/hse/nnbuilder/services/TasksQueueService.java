package org.hse.nnbuilder.services;

import io.grpc.stub.StreamObserver;
import java.time.OffsetDateTime;
import net.devh.boot.grpc.server.service.GrpcService;
import org.hse.nnbuilder.dataset.DatasetStorage;
import org.hse.nnbuilder.dataset.DatasetStored;
import org.hse.nnbuilder.nn.store.NeuralNetworkStorage;
import org.hse.nnbuilder.nn.store.NeuralNetworkStored;
import org.hse.nnbuilder.queue.TaskQueued;
import org.hse.nnbuilder.queue.TaskQueuedStorage;
import org.hse.nnbuilder.services.Tasksqueue.CreateTaskRequest;
import org.hse.nnbuilder.services.Tasksqueue.CreateTaskResponse;
import org.hse.nnbuilder.services.Tasksqueue.GetInformationRequest;
import org.hse.nnbuilder.services.Tasksqueue.GetInformationResponse;
import org.hse.nnbuilder.services.Tasksqueue.TaskStatus;
import org.hse.nnbuilder.services.Tasksqueue.TaskType;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class TasksQueueService extends TasksQueueServiceGrpc.TasksQueueServiceImplBase {

    @Autowired
    private NeuralNetworkStorage neuralNetworkStorage;

    @Autowired
    private DatasetStorage datasetStorage;

    @Autowired
    private TaskQueuedStorage taskQueuedStorage;

    @Override
    public void createTask(CreateTaskRequest request, StreamObserver<CreateTaskResponse> responseObserver) {

        // Get data from request
        TaskType name = request.getName();
        NeuralNetworkStored nnStored = neuralNetworkStorage.getByIdOrThrow(request.getNnId());
        DatasetStored dsStored = datasetStorage.getByIdOrThrow(request.getDatasetId());
        Long epochAmount = request.getEpochAmount();

        // Make a task in DB
        TaskQueued taskQueued = new TaskQueued(name, nnStored, dsStored, epochAmount);
        taskQueuedStorage.saveTaskQueuedTransition(taskQueued, dsStored, nnStored);

        // Response with id of task
        long taskId = taskQueued.getTaskId();
        CreateTaskResponse responseWithTaskId =
                CreateTaskResponse.newBuilder().setTaskId(taskId).build();
        responseObserver.onNext(responseWithTaskId);
        responseObserver.onCompleted();
    }

    @Override
    public void getInformation(GetInformationRequest request, StreamObserver<GetInformationResponse> responseObserver) {

        // Get data and task from request
        long taskId = request.getTaskId();
        TaskQueued tq = taskQueuedStorage.getByIdOrThrow(taskId);

        // Response with info of task
        TaskStatus taskStatus = tq.getTaskStatus();
        OffsetDateTime startTaskTime = tq.getStartTaskTime();
        OffsetDateTime currentTime = OffsetDateTime.now();

        Long timeDeltaSeconds = null;
        if (taskStatus == TaskStatus.Processing) {
            timeDeltaSeconds = currentTime.toEpochSecond() - startTaskTime.toEpochSecond();
        } else if (taskStatus == TaskStatus.Done || taskStatus == TaskStatus.Failed) {
            timeDeltaSeconds = tq.getFinishTaskTime().toEpochSecond() - startTaskTime.toEpochSecond();
        } else {
            // (taskStatus == TaskStatus.HaveNotStarted)
            timeDeltaSeconds = 0L;
        }

        GetInformationResponse responseWithInfo = GetInformationResponse.newBuilder()
                .setTimeSpentOnLearningSec(timeDeltaSeconds)
                .setTaskStatus(taskStatus)
                .build();
        responseObserver.onNext(responseWithInfo);
        responseObserver.onCompleted();
    }
}
