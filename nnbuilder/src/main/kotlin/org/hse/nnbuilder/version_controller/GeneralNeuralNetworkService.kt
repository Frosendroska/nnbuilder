package org.hse.nnbuilder.version_controller

import org.hse.nnbuilder.nn.store.NeuralNetworkService
import org.springframework.stereotype.Service

@Service
class GeneralNeuralNetworkService(
        private val generalNeuralNetworkRepository: GeneralNeuralNetworkRepository,
        private val neuralNetworkService: NeuralNetworkService
) {

    fun create(): GeneralNeuralNetwork {
        val generalNeuralNetwork = GeneralNeuralNetwork(id = 0)
        generalNeuralNetworkRepository.save(generalNeuralNetwork)
        return generalNeuralNetwork
    }

    fun getById(id: Long): GeneralNeuralNetwork {
        return generalNeuralNetworkRepository.getById(id)
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
    fun addNewVersion(id: Long) {
        val generalNeuralNetwork = getByIdOfNNVersion(id)
        neuralNetworkService.addNewVersion(id, generalNeuralNetwork)
    }

}