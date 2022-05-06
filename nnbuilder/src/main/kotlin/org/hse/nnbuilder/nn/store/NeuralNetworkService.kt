package org.hse.nnbuilder.nn.store

import org.hse.nnbuilder.nn.AbstractNeuralNetwork
import org.hse.nnbuilder.nn.ConvolutionalNN
import org.hse.nnbuilder.nn.FeedForwardNN
import org.hse.nnbuilder.nn.LongShortTermMemoryNN
import org.hse.nnbuilder.nn.RecurrentNN
import org.hse.nnbuilder.services.Nnmodification
import org.hse.nnbuilder.version_controller.GeneralNeuralNetwork
import org.springframework.stereotype.Service

@Service
class NeuralNetworkService(
        private val neuralNetworkRepository: NeuralNetworkRepository
) {
    /**
     * Get NeuralNetworkStored by id
     */
    fun getById(id: Long): NeuralNetworkStored {
        return neuralNetworkRepository.getById(id)
    }

    /**
     * Returns AbstractNeuralNetwork from NeuralNetworkStored found by id
     */
    fun getNeuralNetworkById(id: Long): AbstractNeuralNetwork {
        return neuralNetworkRepository.getById(id).neuralNetwork
    }

    private fun createNeuralNetworkStored(abstractNeuralNetwork: AbstractNeuralNetwork, generalNeuralNetwork: GeneralNeuralNetwork) {
        val nnStored: NeuralNetworkStored = NeuralNetworkStored(abstractNeuralNetwork, generalNeuralNetwork)
        neuralNetworkRepository.save(nnStored);
    }

    /**
     * New version - snapshot of neural network with given id - will be added to (the project) generalNeuralNetwork
     * @param id
     * @param generalNeuralNetwork
     */
    fun addNewVersion(id: Long, generalNeuralNetwork: GeneralNeuralNetwork) {
        val oldNNVersion = getNeuralNetworkById(id)

        if (oldNNVersion.nnType == Nnmodification.NetworkType.FF) {
            val ffnn = FeedForwardNN(oldNNVersion as FeedForwardNN)
            createNeuralNetworkStored(ffnn, generalNeuralNetwork)
        } else if (oldNNVersion.nnType == Nnmodification.NetworkType.RNN) {
            val rnn = RecurrentNN(oldNNVersion as RecurrentNN)
            createNeuralNetworkStored(rnn, generalNeuralNetwork)
        } else if (oldNNVersion.nnType == Nnmodification.NetworkType.LSTM) {
            val lstmnn = LongShortTermMemoryNN(oldNNVersion as LongShortTermMemoryNN)
            createNeuralNetworkStored(lstmnn, generalNeuralNetwork)
        } else if (oldNNVersion.nnType == Nnmodification.NetworkType.CNN) {
            val cnn = ConvolutionalNN(oldNNVersion as ConvolutionalNN)
            createNeuralNetworkStored(cnn, generalNeuralNetwork)
        }

    }


}