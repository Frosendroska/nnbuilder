package org.hse.nnbuilder.modification_types

import org.hse.nnbuilder.nn.AbstractNeuralNetwork
import org.hse.nnbuilder.nn.Layer
import org.hse.nnbuilder.nn.store.NeuralNetworkStored

class DelLayer(index: Int, deletedLayer: Layer) : Modification(ModificationType.DelLayer) {
    private val index: Int
    private val deletedLayer: Layer

    init {
        this.index = index
        this.deletedLayer = deletedLayer
    }

    @Override
    override fun makeDirectChange(nn: AbstractNeuralNetwork) {
        nn.delLayer(index)
    }

    @Override
    override fun makeReverseChange(nn: AbstractNeuralNetwork) {
        nn.layers.add(index, deletedLayer)
    }
}