import sys
import pandas as pd
import io
import constructor as ctor
import psycopg2
import json
import torch
import structures
import random
from psycopg2 import sql

curr_id = int(sys.argv[1])

con = psycopg2.connect(
  database="nnbuilder",
  user="nnbuilder",
  password="nnbuilder",
  host="127.0.0.1",
  port="5432"
)

cur = con.cursor()

cur.execute("SELECT * from tasksqueue where task_id=%s", [curr_id])
row = cur.fetchone()
task_info = structures.Task(row)

cur.execute("SELECT * from datasets where ds_id=%s", [task_info.dataset])
row = cur.fetchone()
dataset = structures.Dataset(row)
cur.execute("SELECT * from neuralnetworks where nn_id=%s", [task_info.nnDescription])
row = cur.fetchone()

info = json.loads(row[1])
model = ctor.NeuralNetwork(info, task_info.task_type)

if task_info.model is not None:
    cur.execute("SELECT * from models where m_id=%s", [task_info.model])
    model_params = cur.fetchone()
    ctor.restore_net(model_params[1], model)

if task_info.task_type == structures.TaskType.applyToData:
    res = ctor.get_prediction(model, dataset.data)
    df = pd.DataFrame(dict(label=res))
    data = df.to_csv(index=False)

    id_value = random.randint(0, 10000000)
    cur.execute("INSERT INTO answers (a_id, answers, task_task_id) VALUES (%s, %s, %s);", (id_value, data, curr_id))
    cur.execute("UPDATE tasksqueue SET a_id = %s WHERE task_id = %s", (id_value, curr_id))

else:
    history = []
    optimizer = torch.optim.Adam(model.parameters(), lr=info['learningRate'])
    ctor.train(model, dataset, optimizer, history, task_info.epoch)
    network = ctor.save(model, optimizer)
    id_value = random.randint(0, 10000000)
    cur.execute("INSERT INTO models (m_id, model, task_task_id) VALUES (%s, %s, %s);", (id_value, network, curr_id))
    cur.execute("UPDATE tasksqueue SET m_id = %s WHERE task_id = %s", (id_value, curr_id))

cur.execute("UPDATE tasksqueue SET task_status = %s WHERE task_id = %s", (2, curr_id))
con.commit()
con.close()
