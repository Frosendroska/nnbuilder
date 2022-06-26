import sys
import pandas as pd
import constructor as ctor
import structures
import psycopg2
import json
import torch
import random
import logging
from function_for_db import upload, insert, update, connect

task_id = int(sys.argv[1])


con = connect()
cur = con.cursor()

task_info = structures.Task(upload(cur, "tasksqueue", "task_id", task_id))
dataset = structures.Dataset(upload(cur, "datasets", "ds_id", task_info.dataset))
info = json.loads(upload(cur, "neuralnetworks", "nn_id", task_info.nnDescription)[1])

model = ctor.NeuralNetwork()
model.load_info(info)

if task_info.model is not None:
    model_params = upload(cur, "models", "m_id", task_info.model)
    ctor.restore_net(model_params[1], model)

if task_info.task_type == structures.TaskType.applyToData:
    res = ctor.get_prediction(model, dataset.data)
    df = pd.DataFrame(dict(label=res))
    data = df.to_csv(index=False)

    id_value = random.randint(0, 10000000)
    insert(cur, "answers", "a_id", "answers", id_value, data, task_id)
    update(cur, "a_id", id_value, task_id)

else:
    nn_type = structures.TrainType(int(task_info.task_type))
    if model.type is None:
        model.load_type(nn_type)
    history = []
    optimizer = torch.optim.Adam(model.parameters(), lr=info['learningRate'])
    ctor.train(model, dataset, optimizer, history, task_info.epoch)
    network = ctor.save(model, optimizer, history)
    id_value = random.randint(0, 10000000)
    insert(cur, "models", "m_id", "model", id_value, network, task_id)
    update(cur, "m_id", id_value, task_id)

logging.info("Done")

con.commit()
con.close()
