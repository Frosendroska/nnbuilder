import random
from constructor.src import example
import psycopg2
import os
from sklearn.datasets import make_moons
import pandas as pd

def generate_dataset_in_db(input_file, cur):
    ff = open(input_file, "rb")
    data = ff.read()
    ff.close()
    id_value = random.randint(0, 10000000)
    cur.execute(
        "INSERT INTO datasets (ds_id, content) VALUES (%s, %s);", (id_value, data)
    )

def generate_data(cur):
    X, y = make_moons(n_samples=100, noise=0.1)
    df = pd.DataFrame(dict(x=X[:, 0], y=X[:, 1], label=y))
    df = df.loc[:, df.columns != 'label']
    data = df.to_csv(index=False)
    id_value = random.randint(0, 10000000)
    cur.execute(
        "INSERT INTO datasets (ds_id, content) VALUES (%s, %s);", (id_value, data)
    )
    print(df)

def generate_model_in_db(json_file, cur):
    info = example.Information(json_file)
    my_model = example.NeuralNetwork(info)
    example.save("model.pth", my_model)
    ff = open("model.pth", "rb")
    data = ff.read()
    ff.close()
    id_value = random.randint(0, 10000000)
    cur.execute(
        "INSERT INTO models (m_id, model, task_task_id) VALUES (%s, %s, %s);", (id_value, data, 12)
    )
    os.remove("model.pth")

def generate_task_in_db(nn_id_, dataset_id_):
    id_value = random.randint(0, 10000000)

    cur.execute(
        "INSERT INTO tasksqueue VALUES (%s, %s, %s);", (id_value, nn_id_, dataset_id_)
    )


con = psycopg2.connect(
    database="nnbuilder",
    user="nnbuilder",
    password="nnbuilder",
    host="127.0.0.1",
    port="5432"
)

cur = con.cursor()

generate_data(cur)
con.commit()
con.close()
