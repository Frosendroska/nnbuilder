import random
import pandas as pd
from sklearn.datasets import make_regression
from sklearn.datasets import make_classification


def add_layer(count, layer_type, activationF):
    return {"neurons": count, "layerType": layer_type, "activationFunction": activationF}


def generate_json(layers, lr, cnt_layers, nn_type):
    return {"layers": layers, "learningRate": lr,
            "defaultNumberOfLayers": cnt_layers, "nntype": nn_type}


def generate_regression_dataset():
    X, y = make_regression(n_samples=20, n_features=1, n_informative=1, n_targets=1,
                           bias=0.0, effective_rank=None, tail_strength=0.5,
                           noise=10, shuffle=True, coef=False, random_state=None)
    df = pd.DataFrame(dict(x=X[:, 0], label=y))
    return df


def generate_classification_dataset():
    X, y = make_classification(n_samples=1000, n_features=3, n_informative=3, n_redundant=0, n_repeated=0,
                               n_classes=2, n_clusters_per_class=2, class_sep=0.75, flip_y=0, weights=[0.5, 0.5],
                               random_state=17)
    df = pd.DataFrame(dict(x1=X[:, 0], x2=X[:, 1], x3=X[:, 3], label=y))
    return df


def generate_clastarization_dataset():
    X, y = make_classification(n_samples=1000, n_features=4, n_informative=8, n_redundant=0, n_repeated=0,
                               n_classes=5, random_state=17)
    df = pd.DataFrame(dict(x1=X[:, 0], x2=X[:, 1], x3=X[:, 3], x4=X[:, 4], label=y))
    return df


def add_dataset_in_db(cur, df, label):
    data = df.to_csv(index=False)
    id_value = random.randint(0, 10000000)
    cur.execute(
        "INSERT INTO datasets (ds_id, content, target_column_name) VALUES (%s, %s, %s);",
        (id_value, data, label)
    )


def add_task_in_db(cur, nn_id_, dataset_id_):
    id_value = random.randint(0, 10000000)
    cur.execute(
        "INSERT INTO tasksqueue VALUES (%s, %s, %s);", (id_value, nn_id_, dataset_id_)
    )
