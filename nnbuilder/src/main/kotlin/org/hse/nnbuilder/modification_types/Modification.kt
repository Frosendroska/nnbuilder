package org.hse.nnbuilder.modification_types

import org.hse.nnbuilder.nn.store.NeuralNetworkStored

enum class ModificationType {
    AddLayer, DelLayer, ChangeActivationFunction, ChangeNumberOfNeuron
}

abstract class Modification(type: ModificationType) {
    private val modificationType = type
    
    fun getModificationType(): ModificationType {
        return modificationType
    }

    abstract fun makeDirectChange(nnStored: NeuralNetworkStored)

    abstract fun makeReverseChange(nnStored: NeuralNetworkStored)
}