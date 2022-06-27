import psycopg2
import os
from dotenv import load_dotenv, find_dotenv

def upload(cur, table, column, item_id):
    cur.execute(f"SELECT * FROM {table} WHERE {column}={item_id}")
    row = cur.fetchone()
    return row

def update(cur, column, item_id, task_id):
    cur.execute(f"UPDATE tasksqueue SET {column} = {item_id} WHERE task_id = {task_id}")

def insert(cur, table, column1, column2,  item_id, item_context, task_id):
    cur.execute(f"INSERT INTO {table} ({column1}, {column2}, task_task_id) VALUES (%s, %s, %s);", [item_id, item_context, task_id])

def connect():
    load_dotenv(find_dotenv())
    return psycopg2.connect(
        user=os.getenv("DATABASE_USERNAME"),
        password=os.getenv("DATABASE_PASSWORD"),
        host=os.getenv("DATABASE_HOST"),
        port=os.getenv("DATABASE_PORT"),
        database=os.getenv("DATABASE_NAME")
    )