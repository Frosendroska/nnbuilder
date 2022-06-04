package org.hse.nnbuilder.modification_types

import org.hse.nnbuilder.nn.AbstractNeuralNetwork
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
    override fun makeDirectChange(nn: AbstractNeuralNetwork) {
        nn.changeActivationFunction(index, newActivationFunction)
    }

    @Override
    override fun makeReverseChange(nn: AbstractNeuralNetwork) {
        nn.changeActivationFunction(index, oldActivationFunction)
    }
}