import torch
import torch.nn as nn
from torchvision import datasets, transforms
from tqdm import tqdm


class Information():
    def __init__(self, json_file):
        self.count_hidden_layers = json_file['cnt']
        self.type_module = json_file['type']
        self.layers_size = json_file['layers_info']
        self.output_layer = json_file['output']
        self.func_activation = json_file['active_f']
        self.rate = json_file['rate']


activations = nn.ModuleDict([
    ['prelu', nn.RReLU()],
    ['lrelu', nn.LeakyReLU()],
    ['relu', nn.ReLU()],
    ['sigmoid', nn.Sigmoid()],
    ['softmax', nn.Softmax()],
    ['logsoftmax', nn.LogSoftmax()],
    ['elu', nn.ELU()],
])

loss_functions = {'regression': torch.nn.MSELoss(), 'classification': torch.nn.CrossEntropyLoss()}


class CustomDataset(torch.utils.data.Dataset):
    def __init__(self, df, target_list):
        self.df = df
        self.vectors = df['vector'].values
        self.targets = self.df[target_list].values

    def __len__(self):
        return len(self.vectors)

    def __getitem__(self, index):
        return torch.FloatTensor(self.vectors[index]), torch.FloatTensor(self.targets[index])


class MyNeuralNetwork(nn.Module):
    def __init__(self, info):
        super(MyNeuralNetwork, self).__init__()
        self.array = []

        for i in range(info.count_hidden_layers - 1):
            self.array.append(nn.Linear(info.layers_size[i], info.layers_size[i + 1]))
            self.array.append(activations[info.func_activation[i]])

        self.array.append(nn.Linear(info.layers_size[-1], info.output_layer))

        self.layers = nn.Sequential(*self.array)

    def forward(self, x):
        return self.layers(x)


def train_loop(data, target, model, loss_fn, optimizer):
    prediction = model(data)
    loss = loss_fn(prediction, target)
    optimizer.zero_grad()
    loss.backward()
    optimizer.step()


def study(my_model, train_x, train_y, test_x, test_y, criterion1, optimizer, epochs, history):
    for ep in tqdm(range(epochs)):
        train_loop(train_x, train_y, my_model, criterion1, optimizer)
        test_loop(test_x, test_y, my_model, criterion1, history)


def test_loop(data, target, model, loss_fn, history):
    size = len(data)
    test_loss = 0
    with torch.no_grad():
        prediction = model(data).detach()
        test_loss += loss_fn(prediction, target).item()

    test_loss /= size
    history.append(test_loss)


def save(file, network):
    torch.save(network, file)


def restore_net(file, network):
    network = torch.load(file)
    return network


def get_prediction(network, x, type):
    pred = network(x)
    if (type == 'regression'):
        return pred.data.numpy()
    prediction = torch.max(pred, 1)[1]
    return prediction.data.numpy()
