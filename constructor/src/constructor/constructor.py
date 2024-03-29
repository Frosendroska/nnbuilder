import torch.nn as nn
import json
import torch
#import logging

from constructor.structures import TrainType


DEFAULT_EPOCH = 100

activations = nn.ModuleDict([
                ['prelu', nn.RReLU()],
                ['lrelu', nn.LeakyReLU()],
                ['relu', nn.ReLU()],
                ['sigmoid', nn.Sigmoid()],
                ['softmax', nn.Softmax()],
                ['logsoftmax', nn.LogSoftmax()],
                ['elu', nn.ELU()],
    ])


loss_functions = {'MSELoss': torch.nn.MSELoss(), 'CrossEntropy': torch.nn.CrossEntropyLoss(),
                  'BCE': torch.nn.BCELoss()}

layer_types = {
    "InputCell" : lambda input_size, output_size: nn.Linear(input_size, output_size),
    "OutputCell" : lambda input_size, output_size: nn.Linear(input_size, output_size),
    "HiddenCell" : lambda input_size, output_size: nn.Linear(input_size, output_size),
    "RecurrentCell": lambda input_size, output_size: nn.RNNCell(input_size, output_size),
    "MemoryCell": lambda input_size, output_size: nn.LSTMCell(input_size, output_size)
}

class NeuralNetwork(nn.Module):
    def __init__(self):
        super(NeuralNetwork, self).__init__()
        self.array = []
        self.layers = nn.Sequential()
        self.criterion = None
        self.type = None

    def load_info(self, info):
        layers = info['layers']
        for i in range(len(layers) - 1):
            self.array.append(layer_types.get(layers[i]['layerType'])
                              (layers[i]['neurons'], layers[i + 1]['neurons']))
            if layers[i]['activationFunction'] != 'None':
                self.array.append(activations[layers[i]['activationFunction']])
        self.layers = nn.Sequential(*self.array)

    def load_type(self, nntype):
        self.type = nntype
        if nntype == TrainType.classification:
            self.criterion = loss_functions['CrossEntropy']
        else:
            self.criterion = loss_functions['MSELoss']

    def forward(self, x):
        return self.layers(x)


def tensor_to_lists(dictionary):
    new_dict = {}
    for key, value in dictionary.items():
        new_dict[key] = value.tolist()
    return new_dict


def list_to_tensor(dictionary):
    new_dict = {}
    for key, value in dictionary.items():
        new_dict[key] = torch.Tensor(value)
    return new_dict


def train_step(data, target, model, optimizer):
        prediction = model(data)
        loss = model.criterion(prediction, target)
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()


def test_step(data, target, model, history):
    size = len(data)
    test_loss = 0
    with torch.no_grad():
        prediction = model(data).detach()
        test_loss += model.criterion(prediction, target).item()

    test_loss /= size
    if model.type == TrainType.classification:
        history.append(accuracy(prediction, target))
    else:
        history.append(test_loss)

    #logging.info(f"Avg loss: {test_loss:>8f}")


def train(model, dataset, optimizer, history, epochs=DEFAULT_EPOCH):
    X_train, X_test, y_train, y_test = dataset.split()
    for ep in range(epochs):
        #logging.info(f"Epoch {ep + 1} \n-------------------------------")
        train_step(X_train, y_train, model, optimizer)
        test_step(X_test, y_test, model, history)


def save(network, optimizer, history):
    dict = network.state_dict()
    #print(tensor_to_lists(dict))
    state = {
        'state_dict': tensor_to_lists(dict),
        'type': int(network.type),
    }
    info = json.dumps(state)
    return info


def restore_net(states, model):
    state_dict = json.loads(states)
    dict = state_dict['state_dict']
    type = state_dict['type']
    model.load_state_dict(list_to_tensor(dict))
    model.load_type(type)

    return model


def get_prediction(network, x):
    pred = network(x)
    if network.type == 'regression':
        return pred.data.numpy()
    prediction = torch.max(pred, 1)[1]
    return prediction.data.numpy()


def accuracy(data, target):
    data = torch.max(data, 1)[1].data
    correct = (data == target).sum()
    total = target.shape[0]
    return correct / total