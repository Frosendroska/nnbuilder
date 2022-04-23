package org.hse.nnbuilder.nn.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeuralNetworkRepository extends JpaRepository<NeuralNetworkStored, Long> {
}
