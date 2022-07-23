import mariadb

conn_params = {
    'user': 'enki',
    'password': 'asd',
    'host':'localhost'
}

conn = mariadb.connect(**conn_params)
db = conn.cursor()

query = "CREATE DATABASE IF NOT EXISTS products_eb"
db.execute(query)
db.execute("USE products_eb")

query = """CREATE TABLE IF NOT EXISTS users(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    UNIQUE(username))"""
db.execute(query)

query = """CREATE TABLE IF NOT EXISTS global(
    global_price FLOAT UNSIGNED NOT NULL DEFAULT 0,
    global_stock INT UNSIGNED NOT NULL DEFAULT 0,
    global_sell_price FLOAT UNSIGNED NOT NULL DEFAULT 0,
    global_earnings FLOAT NOT NULL DEFAULT 0
)"""
db.execute(query)

query = """CREATE TABLE IF NOT EXISTS classes(
    class_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    total_class_price FLOAT UNSIGNED NOT NULL DEFAULT 0,
    total_class_stock INT UNSIGNED NOT NULL DEFAULT 0,
    total_class_sell_price FLOAT UNSIGNED NOT NULL DEFAULT 0,
    total_class_earnings FLOAT NOT NULL DEFAULT 0,
    img VARCHAR(1000) NOT NULL DEFAULT "https://img.freepik.com/free-vector/404-error-with-character-error-design-template-website_114341-24.jpg?w=2000",
    UNIQUE(name))"""
db.execute(query)

query = """CREATE TABLE IF NOT EXISTS items(
    item_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    price FLOAT UNSIGNED NOT NULL DEFAULT 0,
    sell_price FLOAT UNSIGNED NOT NULL DEFAULT 0,
    total_price FLOAT UNSIGNED NOT NULL DEFAULT 0,
    total_sell_price FLOAT UNSIGNED NOT NULL DEFAULT 0,
    unit_earning FLOAT NOT NULL DEFAULT 0,
    total_earning FLOAT NOT NULL DEFAULT 0,
    stock INT UNSIGNED NOT NULL DEFAULT 0,
    class_id INT NOT NULL,
    img VARCHAR(1000) NOT NULL DEFAULT "https://img.freepik.com/free-vector/404-error-with-character-error-design-template-website_114341-24.jpg?w=2000",
    UNIQUE(name),
    FOREIGN KEY(class_id) REFERENCES classes(class_id))
    """
 
db.execute(query)
query = "INSERT INTO global(global_price, global_stock, global_sell_price, global_earnings) VALUES(?, ?, ?, ?)"
db.execute(query,(0.0, 0, 0.0, 0.0))
conn.commit()
conn.close()
