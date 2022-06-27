import psycopg2

def test_init():
    con = psycopg2.connect(
        database="nnbuilder",
        user="nnbuilder",
        password="nnbuilder",
        host="127.0.0.1",
        port="5432"
    )
    cur = con.cursor()
    con.commit()
    con.close()

test_init()