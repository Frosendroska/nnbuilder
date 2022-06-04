package org.hse.nnbuilder.modification_types

import org.hse.nnbuilder.nn.store.NeuralNetworkStored
import org.hse.nnbuilder.services.Nnmodification

class ChangeActivationFunction(index: Int,
                               oldActivationFunction: Nnmodification.ActivationFunction,
                               newActivationFunction: Nnmodification.ActivationFunction) :
        Modification(ModificationType.ChangeActivationFunction) {

    private val index: Int
    private val oldActivationFunction: Nnmodification.ActivationFunction
    private val newActivationFunction: Nnmodification.ActivationFunction

    init {
        this.index = index
        this.oldActivationFunction = oldActivationFunction
        this.newActivationFunction = newActivationFunction
    }

    @Override
    override fun makeDirectChange(nnStored: NeuralNetworkStored) {
        nnStored.neuralNetwork.changeActivationFunction(index, newActivationFunction)
    }

    @Override
    override fun makeReverseChange(nnStored: NeuralNetworkStored) {
        nnStored.neuralNetwork.changeActivationFunction(index, oldActivationFunction)
    }
}