package org.hse.nnbuilder.nn.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.AttributeConverter;
import org.hse.nnbuilder.nn.AbstractNeuralNetwork;

public final class AbstractNeuralNetworkConverter implements AttributeConverter<AbstractNeuralNetwork, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(AbstractNeuralNetwork neuralNetwork) {
        try {
            return objectMapper.writeValueAsString(neuralNetwork);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AbstractNeuralNetwork convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, AbstractNeuralNetwork.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
