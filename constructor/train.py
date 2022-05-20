import sys
import pandas as pd
import io
import constructor as ctor
import psycopg2
import json
import torch

def load_to_id(cur, table, curr_id):
    cur.execute("SELECT * from %s where id=%s", (table, curr_id))
    row = cur.fetchone()
    return row

curr_id = int(sys.argv[1])

con = psycopg2.connect(
  database="nnbuilder",
  user="nnbuilder",
  password="nnbuilder",
  host="127.0.0.1",
  port="5432"
)


cur = con.cursor()

task_info = load_to_id(cur, 'tasksqueue', curr_id)
data_id, nn_id, task_type = task_info

dataset = load_to_id(cur, 'dataset', data_id)
data_bytes, target_bytes = dataset[1:]

nerual = load_to_id(cur, 'neuralnetwork', nn_id)

info = json.loads(nerual[1])
model = ctor.NeuralNetwork(info)

if (task_type == 0):
    history = []
    optimizer = torch.optim.Adam(model.parameters(), lr=info['learningRate'])
    X_train, X_test, y_train, y_test = ctor.create_tensors(data_bytes, target_bytes)
    ctor.train(model, X_train, y_train, X_test, y_test, optimizer, history)
    network = ctor.save(model)
    cur.execute("INSERT INTO model (id, content) VALUES (%s, %s);", (nn_id, network))

elif (task_type == 1):
    cur.execute("SELECT * from model where id=%s", curr_id)
    rows = cur.fetchone()
    ctor.restore_net(rows[1], model)

    f = io.BytesIO(data_bytes)
    X = pd.read_csv(f, sep=';')

    res = ctor.get_prediction(model, X)
    cur.execute("UPDATE dataset SET target = %s WHERE id = %s", (res, data_id))

else:
    raise Exception("Unknown task type")

con.commit()
con.close()
