package org.hse.nnbuilder.version_controller

import org.hse.nnbuilder.nn.store.NeuralNetworkStored
import org.hse.nnbuilder.services.Enums
import org.hse.nnbuilder.user.User
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * All versions of one Neural Network.
 */
@Entity
@Table(name = "general_neural_network")
class GeneralNeuralNetwork(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE, CascadeType.REFRESH])
    @JoinColumn(name = "owner_id", nullable = false)
    var owner: User,

    @Column
    val name: String,

    @Column
    val actionType: Enums.ActionType?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long = 0

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "generalNeuralNetwork", cascade = [CascadeType.REMOVE])
    private var nnversions: Set<NeuralNetworkStored> = LinkedHashSet()

    fun getId(): Long {
        return id
    }

    fun getNNVersions(): Set<NeuralNetworkStored> {
        return nnversions
    }

    fun checkVersionsExists(): Boolean {
        println(nnversions)
        for (i in nnversions) {
            println("id: " + i.nnId)
        }
        return nnversions.isNotEmpty()
    }
}
