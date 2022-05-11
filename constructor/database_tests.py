import random

import pandas as pd
import io
import constructor
import torch
import psycopg2

def generate_dataset_in_db(input_file, cur):
    ff = open(input_file, "rb")
    data = ff.read()
    id_val = random.randint(0, 10000000)
    cur.execute(
        "INSERT INTO dataset (id, content) VALUES (id_val, data);"
    )

def generate_model_in_db(input_file, cur):
    json_file = {'cnt': 3, 'type': 'regression', 'layers_info': [1, 50, 30], 'output': 1, 'active_f': ['relu', 'relu'],
                 'rate': 0.001}
    info = constructor.Information(json_file)
    my_model = constructor.MyNeuralNetwork(info)
    output = io.BytesIO()
    constructor.save(output, my_model)
    id_val = random.randint(0, 10000000)
    data = output.read()
    cur.execute(
        "INSERT INTO model (id, content) VALUES (id_val, data);"
    )

con = psycopg2.connect(
  database="nnbuilder",
  user="nnbuilder",
  password="nnbuilder",
  host="127.0.0.1",
  port="5432"
)

cur = con.cursor()

generate_dataset_in_db("/Users/evgeniavu/Downloads/Date_Fruit_Datasets/Date_Fruit_Datasets/fruits.csv",
                       cur)
#out_file = open("out-file", "wb")
#out_file.write(data)
#out_file.close()