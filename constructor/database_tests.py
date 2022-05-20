import random
import constructor
import psycopg2
import os

def generate_dataset_in_db(input_file, cur):
    ff = open(input_file, "rb")
    data = ff.read()
    ff.close()
    id_value = random.randint(0, 10000000)
    cur.execute(
        "INSERT INTO dataset (id, content) VALUES (%s, %s);", (id_value, data)
    )

def generate_model_in_db(json_file, cur):
    info = constructor.Information(json_file)
    my_model = constructor.NeuralNetwork(info)
    constructor.save("model.pth", my_model)
    ff = open("model.pth", "rb")
    data = ff.read()
    ff.close()
    id_value = random.randint(0, 10000000)
    cur.execute(
        "INSERT INTO model (id, content) VALUES (%s, %s);", (id_value, data)
    )
    os.remove("model.pth")

def generate_task_in_db(nn_id_, dataset_id_):
    id_value = random.randint(0, 10000000)
    cur.execute(
        "INSERT INTO tasksqueue (id, nn_id, data_id) VALUES (%s, %s, %s);", (id_value, nn_id_, dataset_id_)
    )

con = psycopg2.connect(
    database="nnbuilder",
    user="nnbuilder",
    password="nnbuilder",
    host="127.0.0.1",
    port="5432"
)

cur = con.cursor()

generate_task_in_db(10, 100)

con.commit()
