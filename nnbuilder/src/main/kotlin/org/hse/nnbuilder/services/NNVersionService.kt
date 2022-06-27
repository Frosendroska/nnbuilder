package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.hse.nnbuilder.exception.NeuralNetworkNotFoundException
import org.hse.nnbuilder.nn.Layer
import org.hse.nnbuilder.version_controller.GeneralNeuralNetworkService
import org.springframework.beans.factory.annotation.Autowired
import java.lang.Integer.max

@GrpcService
class NNVersionService : NNVersionServiceGrpcKt.NNVersionServiceCoroutineImplBase() {
    // TODO add authorization
    @Autowired
    private lateinit var generalNeuralNetworkService: GeneralNeuralNetworkService

    @Override
    override suspend fun makeNNSnapshot(request: Nnversion.MakeNNSnapshotRequest): Nnversion.MakeNNSnapshotResponse {
        var exception = ""
        var newVersionId = 0L
        try {
            newVersionId = generalNeuralNetworkService.addNewVersion(request.nnId)
        } catch (e: NeuralNetworkNotFoundException) {
            exception = e.message
        }
        return Nnversion.MakeNNSnapshotResponse.newBuilder()
            .setNnId(newVersionId)
            .setException(exception)
            .build()
    }

    @Override
    override suspend fun deleteNNVersion(request: Nnversion.DeleteNNVersionRequest): Nnversion.DeleteNNVersionResponse {
        val nnId = request.nnId
        generalNeuralNetworkService.deleteNNVersionById(nnId)

        return Nnversion.DeleteNNVersionResponse.newBuilder().build()
    }

    @Override
    override suspend fun deleteProject(request: Nnversion.DeleteProjectRequest): Nnversion.DeleteProjectResponse {
        val projectId = request.projectId
        generalNeuralNetworkService.deleteById(projectId)

        return Nnversion.DeleteProjectResponse.newBuilder().build()
    }

    @Override
    override suspend fun compareNNVersions(request: Nnversion.CompareRequest): Nnversion.CompareResponse {
        val responseBuilder = Nnversion.CompareResponse.newBuilder()

        val nnId1 = request.nnId1
        val nnId2 = request.nnId2
        val (nn1, nn2) = try {
            generalNeuralNetworkService.getNNVersionsToCompare(nnId1, nnId2)
        } catch (e: Exception) {
            return responseBuilder.setException(e.message).build()
        }

        // set diff of learning rate
        if (nn1.learningRate != nn2.learningRate) {
            responseBuilder.hasDiffInLearningRate = true
            responseBuilder.learningRate1 = nn1.learningRate.toDouble()
            responseBuilder.learningRate2 = nn2.learningRate.toDouble()
        } else {
            responseBuilder.hasDiffInLearningRate = false
        }

//        responseBuilder.hasDiffInLearningRate = true
//        if (responseBuilder.hasDiffInLearningRate) {
//            responseBuilder.learningRate1 = nn1.learningRate.toDouble()
//            responseBuilder.learningRate2 = nn2.learningRate.toDouble()
//        }

        // set diff of layers
        val layersDiff = getLayersDiff(nn1.layers, nn2.layers)
        responseBuilder.addAllLayersDiff(layersDiff)

        return responseBuilder.build()
    }

    private fun getLayersDiff(layers1: List<Layer>, layers2: List<Layer>): ArrayList<Nnversion.layerDifference> {
        val maxSize = max(layers1.size, layers2.size)
        val layersDiff = ArrayList<Nnversion.layerDifference>(maxSize)
        for (i in 0 until maxSize) {
            val layerDiffBuilder = Nnversion.layerDifference.newBuilder()

            if (i < layers1.size) {
                layerDiffBuilder.exists1 = true
                val layer = layers1[i]
                layerDiffBuilder.neurons1 = layer.neurons
                layerDiffBuilder.layerType1 = layer.layerType
                layerDiffBuilder.activationFun1 = layer.activationFunction
            }

            if (i < layers2.size) {
                layerDiffBuilder.exists2 = true
                val layer = layers2[i]
                layerDiffBuilder.neurons2 = layer.neurons
                layerDiffBuilder.layerType2 = layer.layerType
                layerDiffBuilder.activationFun2 = layer.activationFunction
            }

            if (layerDiffBuilder.exists1 == layerDiffBuilder.exists2) {
                layerDiffBuilder.hasDiffInExisting = false
                layerDiffBuilder.hasDiffInNeurons = (layerDiffBuilder.neurons1 != layerDiffBuilder.neurons2)
                layerDiffBuilder.hasDiffInLayerType = (!layerDiffBuilder.layerType1.equals(layerDiffBuilder.layerType2))
                layerDiffBuilder.hasDiffInActivationFunction = (!layerDiffBuilder.activationFun1.equals(layerDiffBuilder.activationFun2))
            } else {
                layerDiffBuilder.hasDiffInExisting = true
            }

            layersDiff.add(layerDiffBuilder.build())
        }

        return layersDiff
    }
}
