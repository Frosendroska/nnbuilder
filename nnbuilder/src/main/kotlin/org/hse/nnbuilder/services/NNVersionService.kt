package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.hse.nnbuilder.exception.NeuralNetworkNotFoundException
import org.hse.nnbuilder.version_controller.GeneralNeuralNetworkService
import org.springframework.beans.factory.annotation.Autowired

@GrpcService
open class NNVersionService : NNVersionServiceGrpcKt.NNVersionServiceCoroutineImplBase() {
    // TODO add authorization
    @Autowired
    private lateinit var generalNeuralNetworkService: GeneralNeuralNetworkService

    @Override
    override suspend fun makeNNSnapshot(request: Nnversion.makeNNSnapshotRequest): Nnversion.makeNNSnapshotResponse {
        var exception = ""
        var newVersionId = 0L
        try {
            newVersionId = generalNeuralNetworkService.addNewVersion(request.nnId)
        } catch (e: NeuralNetworkNotFoundException) {
            exception = e.message
        }
        return Nnversion.makeNNSnapshotResponse.newBuilder()
            .setNnId(newVersionId)
            .setException(exception)
            .build()
    }

    @Override
    override suspend fun deleteNNVersion(request: Nnversion.deleteNNVersionRequest): Nnversion.deleteNNVersionResponse {
        val nnId = request.nnId
        generalNeuralNetworkService.deleteNNVersionById(nnId)

        return Nnversion.deleteNNVersionResponse.newBuilder().build()
    }

    @Override
    override suspend fun deleteProject(request: Nnversion.deleteProjectRequest): Nnversion.deleteProjectResponse {
        val projectId = request.projectId
        generalNeuralNetworkService.deleteById(projectId)

        return Nnversion.deleteProjectResponse.newBuilder().build()
    }
}
