package org.hse.nnbuilder.version_controller

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import org.hse.nnbuilder.nn.store.NeuralNetworkStored

/**
 * All versions of one Neural Network.
 */
@Entity
@Table(name = "general_neural_network")
class GeneralNeuralNetwork(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private val id: Long
) {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "generalNeuralNetwork")
    private var nnversions: Set<NeuralNetworkStored> = LinkedHashSet()

    fun getId(): Long {
        return id
    }

    fun getNNVersions(): Set<NeuralNetworkStored> {
        return nnversions
    }
}