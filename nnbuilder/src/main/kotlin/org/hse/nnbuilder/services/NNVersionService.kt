package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.hse.nnbuilder.exception.NeuralNetworkNotFoundException
import org.hse.nnbuilder.version_controller.GeneralNeuralNetworkService
import org.springframework.beans.factory.annotation.Autowired

@GrpcService
open class NNVersionService : NNVersionServiceGrpcKt.NNVersionServiceCoroutineImplBase() {

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
}
