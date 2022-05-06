package org.hse.nnbuilder.nn.store

import javax.persistence.EntityNotFoundException
import org.hse.nnbuilder.exception.NeuralNetworkNotFoundException
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
        return neuralNetworkRepository.findById(id).orElseThrow { NeuralNetworkNotFoundException("Neural Network with id $id does not exist.") }
    }

    /**
     * Returns AbstractNeuralNetwork from NeuralNetworkStored found by id
     */
    fun getNeuralNetworkById(id: Long): AbstractNeuralNetwork {
        return neuralNetworkRepository.getById(id).neuralNetwork
    }

    /**
     * Create NeuralNetworkStored from AbstractNeuralNetwork and GeneralNeuralNetwork (project that should contain new NNStored)
     */
    private fun createNeuralNetworkStored(abstractNeuralNetwork: AbstractNeuralNetwork, generalNeuralNetwork: GeneralNeuralNetwork): Long {
        val nnStored = NeuralNetworkStored(abstractNeuralNetwork, generalNeuralNetwork)
        neuralNetworkRepository.save(nnStored)
        return nnStored.id
    }

    /**
     * New version - snapshot of neural network with given id - will be added to (the project) generalNeuralNetwork
     * @param id
     * @param generalNeuralNetwork
     */
    fun addNewVersion(id: Long, generalNeuralNetwork: GeneralNeuralNetwork): Long {
        val oldNNVersion = getNeuralNetworkById(id)

        if (oldNNVersion.nnType == Nnmodification.NetworkType.FF) {
            val ffnn = FeedForwardNN(oldNNVersion as FeedForwardNN)
            return createNeuralNetworkStored(ffnn, generalNeuralNetwork)
        } else if (oldNNVersion.nnType == Nnmodification.NetworkType.RNN) {
            val rnn = RecurrentNN(oldNNVersion as RecurrentNN)
            return createNeuralNetworkStored(rnn, generalNeuralNetwork)
        } else if (oldNNVersion.nnType == Nnmodification.NetworkType.LSTM) {
            val lstmnn = LongShortTermMemoryNN(oldNNVersion as LongShortTermMemoryNN)
            return createNeuralNetworkStored(lstmnn, generalNeuralNetwork)
        } else if (oldNNVersion.nnType == Nnmodification.NetworkType.CNN) {
            val cnn = ConvolutionalNN(oldNNVersion as ConvolutionalNN)
            return createNeuralNetworkStored(cnn, generalNeuralNetwork)
        }
        return 0
    }


}