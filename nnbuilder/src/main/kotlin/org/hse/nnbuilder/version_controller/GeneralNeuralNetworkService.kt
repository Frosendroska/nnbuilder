package org.hse.nnbuilder.version_controller

import org.springframework.stereotype.Service

@Service
class GeneralNeuralNetworkService(
        private val generalNeuralNetworkRepository: GeneralNeuralNetworkRepository
) {
    fun create(): GeneralNeuralNetwork {
        val generalNeuralNetwork = GeneralNeuralNetwork(id = 0)
        //GeneralNeuralNetwork generalNeuralNetwork = generalNeuralNetworkRepository.getById(27L);
        generalNeuralNetworkRepository.save(generalNeuralNetwork)
        println("correct ID: " + generalNeuralNetwork.getId())

        return generalNeuralNetwork
    }

    fun getById(id: Long): GeneralNeuralNetwork {
        return generalNeuralNetworkRepository.getById(id)
    }

    /**
     * Add new version - snapshot of neural network with given id
     * @param id
     */
    fun addNewVersion(id: Long) {

    }

}