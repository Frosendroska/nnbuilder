import enum
import io
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OrdinalEncoder
import torch


class TrainType(enum.Enum):
    classification = 0
    regression = 1

    def __new__(cls, value):
        member = object.__new__(cls)
        member._value_ = value
        return member

    def __int__(self):
        return self.value

class TaskType(enum.Enum):
    trainClassification = 0
    trainRegression = 1
    applyToData = 2

    def __new__(cls, value):
        member = object.__new__(cls)
        member._value_ = value
        return member

    def __int__(self):
        return self.value


class Task(object):
    def __init__(self, args):
        self.epoch = args[2]
        self.task_type = TaskType(args[6])

        self.dataset = args[7]
        self.nnDescription = args[8]
        self.model = args[9]


def prepare_data(df):
    df = df.dropna()
    ord_enc = OrdinalEncoder()
    col = df.select_dtypes(include=['object']).columns
    df[col] = ord_enc.fit_transform(df[col])


class Dataset(object):
    def __init__(self, args):
        data_bytes, target_name = args[1:]
        f = io.BytesIO(data_bytes)
        df = pd.read_csv(f)
        if target_name != None:
            self.data = torch.tensor(df.loc[:, df.columns != target_name].values.astype(np.float32))
            target_value = df[target_name].values.astype(np.float32)
            self.target = torch.tensor(np.reshape(target_value, (len(target_value), 1)))

        else:
            self.data = torch.tensor(df.values.astype(np.float32))
            self.target = None

    def split(self):
        return train_test_split(self.data, self.target, train_size=0.67, random_state=42)
