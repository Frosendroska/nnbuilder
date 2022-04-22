package org.hse.nnbuilder.nn;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NNRepository  extends JpaRepository<AbstractNeuralNetwork, Long> {
    @NotNull
    Optional<AbstractNeuralNetwork> findById(@NotNull Long id);
}
