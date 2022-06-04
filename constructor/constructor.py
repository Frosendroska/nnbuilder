import torch
import torch.nn as nn
from torchvision import datasets, transforms
import pandas as pd
import io
import json
import torch
from sklearn.model_selection import train_test_split
import random
import pandas
import structures
import ast
import types
import argparse

activations = nn.ModuleDict([
                ['prelu', nn.RReLU()],
                ['lrelu', nn.LeakyReLU()],
                ['relu', nn.ReLU()],
                ['sigmoid', nn.Sigmoid()],
                ['softmax', nn.Softmax()],
                ['logsoftmax', nn.LogSoftmax()],
                ['elu', nn.ELU()],
    ])

loss_functions = {'MSELoss': torch.nn.MSELoss(), 'CrossEntropy': torch.nn.CrossEntropyLoss()}

class CustomDataset(torch.utils.data.Dataset):
    def __init__(self, df, target_list):
        self.df = df
        self.vectors = df['vector'].values
        self.targets = self.df[target_list].values

    def __len__(self):
        return len(self.vectors)

    def __getitem__(self, index):
        return torch.FloatTensor(self.vectors[index]), torch.FloatTensor(self.targets[index])


class NeuralNetwork(nn.Module):
    def __init__(self, info, type):
        super(NeuralNetwork, self).__init__()
        self.array = []
        layers = info['layers']
        for i in range(len(layers) - 1):
            self.array.append(nn.Linear(layers[i]['neurons'], layers[i + 1]['neurons']))
            if layers[i]['activationFunction'] != 'None':
                self.array.append(activations[layers[i]['activationFunction']])
        self.layers = nn.Sequential(*self.array)
        if type == structures.TaskType.trainClassification:
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


def train_loop(data, target, model, optimizer):
        prediction = model(data)
        loss = model.criterion(prediction, target)
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()


def train(model, dataset, optimizer, history, epochs=100):
    X_train, X_test, y_train, y_test = dataset.split()
    for ep in range(epochs):
        print(f"Epoch {ep + 1} \n-------------------------------")
        train_loop(X_train, y_train, model, optimizer)
        test_loop(X_test, y_test, model, history)


def test_loop(data, target, model, history):
    size = len(data)
    test_loss = 0
    with torch.no_grad():
        prediction = model(data).detach()
        test_loss += model.criterion(prediction, target).item()

    test_loss /= size
    history.append(test_loss)
    print(f"Avg loss: {test_loss:>8f}")


def save(network, optimizer):
    dict = network.state_dict()
    tensor_to_lists(dict)
    state = {
        'state_dict': dict
    }
    info = json.dumps(state)
    return info


def restore_net(states, model):
    state_dict = json.loads(states)
    dict = state_dict['state_dict']
    list_to_tensor(dict)
    model.load_state_dict(dict)
    return model


def get_prediction(network, x, type='classification'):
    pred = network(x)
    if type == 'regression':
        return pred.data.numpy()
    prediction = torch.max(pred, 1)[1]
    return prediction.data.numpy()


def accuracy(model, data, target):
    total = 0
    correct = 0
    for X, y in (zip(data, target)):
        res = model(X)
        total += 1
        correct += (abs(res - y) <= 0.5).sum()
    return correct / total