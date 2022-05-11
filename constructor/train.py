import sys
import pandas as pd
import io
import constructor
import psycopg2
import json
import torch
from sklearn.model_selection import train_test_split
import random
import pandas

curr_id = int(sys.argv[1])

con = psycopg2.connect(
  database="nnbuilder",
  user="nnbuilder",
  password="nnbuilder",
  host="127.0.0.1",
  port="5432"
)

cur = con.cursor()
cur.execute("SELECT * from taskqueue where id={}".format(curr_id))

rows = cur.fetchall()
if (len(rows) != 1):
  raise ValueError

data_id = rows[0][1]
nn_id = rows[0][2]
task_type = rows[0][4]

cur.execute("SELECT * from dataset where id={}".format(curr_id))
rows = cur.fetchall()
data_bytes = rows[0][1]
target_bytes = rows[0][2]

if (task_type == 0):
    cur.execute("SELECT * from nerualnetwork where id={}".format(curr_id))
    rows = cur.fetchall()
    nn_json = rows[0][1]
    info = json.loads(nn_json)
    my_model = constructor.MyNeuralNetwork(info)
    optimizer = torch.optim.Adam(my_model.parameters(), lr=info.rate)
    criterion = constructor.loss_functions[info.type_module]
    history = []
    f = io.BytesIO(data_bytes)
    X = pd.read_csv(f, sep=';')
    f = io.BytesIO(target_bytes)
    y = pd.read_csv(f, sep=';')
    X_train, X_test, y_train, y_test = train_test_split(X, y,
                                                        train_size=0.67,
                                                        random_state=42)
    constructor.study(my_model, X_train, y_train, X_test, y_test, criterion,
                      optimizer, 80, history)

    output = io.BytesIO()
    constructor.save(output, my_model)
    data = output.read()
    cur.execute(
        "INSERT INTO model (id, content) VALUES (nn_id, data);"
    )

else:
    cur.execute("SELECT * from model where id={}".format(curr_id))
    rows = cur.fetchall()
    model_byte = io.BytesIO(rows[0][1])
    model = None
    constructor.restore_net(model_byte, model)
    f = io.BytesIO(data_bytes)
    X = pd.read_csv(f, sep=';')
    res = constructor.get_prediction(model, X)

con.commit()
con.close()
