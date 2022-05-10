import sys
import constructor
import psycopg2

id = sys.argv[1]

con = psycopg2.connect(
  database="nnbuilder",
  user="nnbuilder",
  password="nnbuilder",
  host="127.0.0.1",
  port="5432"
)
