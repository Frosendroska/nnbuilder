import torch.nn as nn
import json
import torch
import structures
import logging
import enum
from structures import TrainType

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
            self.array.append(nn.Linear(layers[i]['neurons'], layers[i + 1]['neurons']))
            if layers[i]['activationFunction'] != 'None':
                self.array.append(activations[layers[i]['activationFunction']])
        self.layers = nn.Sequential(*self.array)

    def load_type(self, nntype):
        self.type = nntype
        if nntype == structures.TrainType.classification:
            self.criterion = loss_functions['CrossEntropy']
        else:
            self.criterion = loss_functions['MSELoss']

    def forward(self, x):
        return self.layers(x)


def tensor_to_lists(dictionary):
    for key, value in dictionary.items():
        dictionary[key] = value.tolist()


def list_to_tensor(dictionary):
    for key, value in dictionary.items():
        dictionary[key] = torch.Tensor(value)


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

    logging.info(f"Avg loss: {test_loss:>8f}")

def train(model, dataset, optimizer, history, epochs=DEFAULT_EPOCH):
    X_train, X_test, y_train, y_test = dataset.split()
    for ep in range(epochs):
        logging.info(f"Epoch {ep + 1} \n-------------------------------")
        train_step(X_train, y_train, model, optimizer)
        test_step(X_test, y_test, model, history)

def save(network, optimizer, history):
    dict = network.state_dict()
    tensor_to_lists(dict)
    state = {
        'state_dict': dict,
        'type': network.type,
        'train metrics': history,
        'optimizer': optimizer.state_dict()
    }
    info = json.dumps(state)
    return info

def restore_net(states, model):
    state_dict = json.loads(states)
    dict = state_dict['state_dict']
    type = state_dict['type']
    list_to_tensor(dict)
    model.load_state_dict(dict)
    model.load_type(type)

    return model


def get_prediction(network, x, type="classification"):
    pred = network(x)
    if type == 'regression':
        return pred.data.numpy()
    prediction = torch.max(pred, 1)[1]
    return prediction.data.numpy()


def accuracy(data, target):
    correct = (int(data == target)).sum()
    total = target.shape[0]
    return correct / total