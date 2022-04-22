package org.hse.nnbuilder.nn;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class NNService {
    @NotNull
    public AbstractNeuralNetwork findById(@NotNull Long id) {
        return NNRepository.save();
    }
}
