import constructor
import test_tools

layer_type = ["InputCell", "OutputCell", "HiddenCell", "RecurrentCell", "MemoryCell"]
activation_type = ["relu", "softmax", "sigmoid"]

DEFAULT_LR = 0.01


def ff_classification():
    layers = []
    layers.append(test_tools.add_layer(8, layer_type[0], activation_type[0]))
    layers.append(test_tools.add_layer(50, layer_type[2], activation_type[0]))
    layers.append(test_tools.add_layer(50, layer_type[2], activation_type[0]))
    layers.append(test_tools.add_layer(5, layer_type[1], activation_type[0]))

    return test_tools.generate_json(layers, DEFAULT_LR, len(layers), "FF")


def ff_regression():
    layers = []
    layers.append(test_tools.add_layer(8, layer_type[0], activation_type[0]))
    layers.append(test_tools.add_layer(50, layer_type[2], activation_type[0]))
    layers.append(test_tools.add_layer(50, layer_type[2], activation_type[0]))
    layers.append(test_tools.add_layer(5, layer_type[1], activation_type[0]))

    return test_tools.generate_json(layers, DEFAULT_LR, len(layers), "FF")


def LSTM():
    layers = []
    layers.append(test_tools.add_layer(8, layer_type[0], activation_type[0]))
    layers.append(test_tools.add_layer(50, layer_type[4], activation_type[0]))
    layers.append(test_tools.add_layer(50, layer_type[4], activation_type[0]))
    layers.append(test_tools.add_layer(5, layer_type[1], activation_type[0]))

    return test_tools.generate_json(layers, DEFAULT_LR, len(layers), "FF")


def RNN():
    layers = []
    layers.append(test_tools.add_layer(8, layer_type[0], activation_type[0]))
    layers.append(test_tools.add_layer(50, layer_type[3], activation_type[0]))
    layers.append(test_tools.add_layer(50, layer_type[3], activation_type[0]))
    layers.append(test_tools.add_layer(5, layer_type[1], activation_type[0]))

    return test_tools.generate_json(layers, DEFAULT_LR, len(layers), "FF")