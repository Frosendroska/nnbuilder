import sys
import numpy.random
import pandas as pd
import constructor as ctor
import structures
import psycopg2
import json
import torch
import random
import logging
from function_for_db import upload, insert, update, connect


def train():
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

        generate_answer_id = numpy.random.randint(1000000)
        insert(cur, "answers", "a_id", "answers", generate_answer_id, data, task_id)
        update(cur, "a_id", generate_answer_id, task_id)

    else:
        nn_type = structures.TrainType(int(task_info.task_type))
        if model.type is None:
            model.load_type(nn_type)
        history = []
        optimizer = torch.optim.Adam(model.parameters(), lr=info['learningRate'])
        ctor.train(model, dataset, optimizer, history, task_info.epoch)
        network = ctor.save(model, optimizer, history)
        generate_model_id = numpy.random.randint(1000000)
        insert(cur, "models", "m_id", "model", generate_model_id, network, task_id)
        update(cur, "m_id", generate_model_id, task_id)

    logging.info("Done")

    con.commit()
    con.close()

if __name__ == '__main__':
    train()
