package org.hse.nnbuilder.version_controller

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GeneralNeuralNetworkRepository : JpaRepository<GeneralNeuralNetwork, Long>
