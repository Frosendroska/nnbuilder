package org.hse.nnbuilder.version_controller

import org.hse.nnbuilder.exception.NeuralNetworkNotFoundException
import org.hse.nnbuilder.nn.AbstractNeuralNetwork
import org.hse.nnbuilder.nn.store.NeuralNetworkService
import org.hse.nnbuilder.user.User
import org.springframework.stereotype.Service

@Service
class GeneralNeuralNetworkService(
        private val generalNeuralNetworkRepository: GeneralNeuralNetworkRepository,
        private val neuralNetworkService: NeuralNetworkService
) {

    fun create(user: User): GeneralNeuralNetwork {
        val generalNeuralNetwork = GeneralNeuralNetwork(user)
        generalNeuralNetworkRepository.save(generalNeuralNetwork)
        return generalNeuralNetwork
    }

    private fun checkExistsById(id: Long): Boolean {
        return generalNeuralNetworkRepository.findById(id).isPresent
    }

    fun getById(id: Long): GeneralNeuralNetwork {
        return generalNeuralNetworkRepository.findById(id).orElseThrow { NeuralNetworkNotFoundException("GeneralNeuralNetwork with id $id does not exist.") }
    }

    /**
     *
     */
    fun deleteById(id: Long) {
        if (checkExistsById(id)) {
            generalNeuralNetworkRepository.deleteById(id)
        }
    }

    fun deleteNNVersionById(id: Long) {
        val projectId = getProjectIdByNNVersionId(id)
        neuralNetworkService.deleteById(id)

        if (!getById(projectId).checkVersionsExists()) { // we deleted last version in this project
            deleteById(projectId)
        }
        // println(gnn.getNNVersions())
    }

    /**
     * Returns two neural network versions
     */
    fun getNNVersionsToCompare(nnId1: Long, nnId2: Long): Pair<AbstractNeuralNetwork, AbstractNeuralNetwork> {
        if (getProjectIdByNNVersionId(nnId2) != getProjectIdByNNVersionId(nnId2)) {
            throw Exception("These neural networks do not belong to the same project.")
        }
        val nn1 = neuralNetworkService.getById(nnId1).neuralNetwork
        val nn2 = neuralNetworkService.getById(nnId2).neuralNetwork

        return Pair(nn1, nn2)
    }

    /**
     * Get GeneralNeuralNetwork that contains NNVersion with given id
     */
    private fun getByIdOfNNVersion(id: Long): GeneralNeuralNetwork {
        val NNVersionStored = neuralNetworkService.getById(id)
        return NNVersionStored.generalNeuralNetwork
    }

    /**
     * Get id of GeneralNeuralNetwork that contains NNVersion with given id
     */
    private fun getProjectIdByNNVersionId(id: Long): Long {
        return getByIdOfNNVersion(id).getId()
    }

    /**
     * Add new neural network version - snapshot of neural network with given id
     * @param id
     */
    fun addNewVersion(id: Long): Long {
        val generalNeuralNetwork = getByIdOfNNVersion(id)
        return neuralNetworkService.addNewVersion(id, generalNeuralNetwork)
    }
}
