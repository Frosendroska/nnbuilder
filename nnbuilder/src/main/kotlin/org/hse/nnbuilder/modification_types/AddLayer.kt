package org.hse.nnbuilder.modification_types

import org.hse.nnbuilder.nn.AbstractNeuralNetwork
import org.hse.nnbuilder.nn.store.NeuralNetworkStored
import org.hse.nnbuilder.services.Nnmodification

class AddLayer(index: Int, layerType: Nnmodification.LayerType) : Modification(ModificationType.AddLayer) {
    private val index: Int
    private val layerType: Nnmodification.LayerType

    init {
        this.index = index
        this.layerType = layerType
    }

    @Override
    override fun makeDirectChange(nn: AbstractNeuralNetwork) {
        nn.addLayer(index, layerType)
    }

    @Override
    override fun makeReverseChange(nn: AbstractNeuralNetwork) {
        nn.delLayer(index)
    }
}