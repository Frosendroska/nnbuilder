package org.hse.nnbuilder.modification_types

import org.hse.nnbuilder.nn.AbstractNeuralNetwork

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
    override fun makeDirectChange(nn: AbstractNeuralNetwork) {
        nn.changeNumberOfNeuron(index, newNumber)
    }

    @Override
    override fun makeReverseChange(nn: AbstractNeuralNetwork) {
        nn.changeNumberOfNeuron(index, oldNumber)
    }
}
