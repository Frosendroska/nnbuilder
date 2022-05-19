package org.hse.nnbuilder.version_controller

import org.hse.nnbuilder.exception.NeuralNetworkNotFoundException
import org.hse.nnbuilder.nn.store.NeuralNetworkService
import org.hse.nnbuilder.user.User
import org.springframework.stereotype.Service
import javax.transaction.Transactional

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

    fun deleteById(id: Long) {
        if (checkExistsById(id)) {
            generalNeuralNetworkRepository.deleteById(id)
        }
    }

    /**
     * Get GeneralNeuralNetwork that contains NNVersion with given id
     */
    fun getByIdOfNNVersion(id: Long): GeneralNeuralNetwork {
        val NNVersionStored = neuralNetworkService.getById(id)
        return NNVersionStored.generalNeuralNetwork
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
