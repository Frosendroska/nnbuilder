package org.hse.nnbuilder.modification_types

import org.hse.nnbuilder.nn.store.NeuralNetworkStored

class ChangeNumberOfNeuron(index: Int, oldNumber: Long, newNumber: Long) :
        Modification(ModificationType.ChangeNumberOfNeuron) {
    private val index: Int
    private val oldNumber: Long
    private val newNumber: Long

    init {
        this.index = index
        this.oldNumber = oldNumber
        this.newNumber = newNumber
    }

    @Override
    override fun makeDirectChange(nnStored: NeuralNetworkStored) {
        nnStored.neuralNetwork.changeNumberOfNeuron(index, newNumber)
    }

    @Override
    override fun makeReverseChange(nnStored: NeuralNetworkStored) {
        nnStored.neuralNetwork.changeNumberOfNeuron(index, oldNumber)
    }
}