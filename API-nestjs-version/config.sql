create database IF NOT EXISTS nest_eb;

use nest_eb;


CREATE TABLE global(
	id INT PRIMARY KEY NOT NULL,
	global_buy_price FLOAT NOT NULL DEFAULT 0,
	global_sell_price FLOAT NOT NULL DEFAULT 0,
	global_stock INT NOT NULL DEFAULT 0,
	global_earning FLOAT GENERATED ALWAYS AS (global_sell_price - global_buy_price) STORED
);


INSERT INTO global (id) VALUES(1);

CREATE TABLE tproducts( 
	name VARCHAR(20) PRIMARY KEY NOT NULL UNIQUE, 
	total_buy_price FLOAT NOT NULL DEFAULT 0.0,
	total_sell_price FLOAT NOT NULL DEFAULT 0.0,
	total_earning FLOAT GENERATED ALWAYS AS (total_sell_price - total_buy_price) STORED,
	stock INT NOT NULL DEFAULT 0,
	global_id INT NOT NULL DEFAULT 1,
	FOREIGN KEY (global_id) REFERENCES global(id) ON DELETE CASCADE
);

CREATE TABLE items( 
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, 
	name VARCHAR(20) NOT NULL UNIQUE, 
	buy_price FLOAT NOT NULL DEFAULT 0.0, 
	sell_price FLOAT NOT NULL DEFAULT 0.0, 
	stock INT NOT NULL DEFAULT 0,
	total_buy_price FLOAT GENERATED ALWAYS AS (buy_price * stock) STORED,
	total_sell_price FLOAT GENERATED ALWAYS AS (sell_price * stock) STORED,
	total_earning FLOAT GENERATED ALWAYS AS (total_sell_price - total_buy_price) STORED,
	unit_earning FLOAT GENERATED ALWAYS AS (sell_price - buy_price) STORED,
	type_name VARCHAR(20), 
	FOREIGN KEY (type_name) REFERENCES tproducts(name) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TRIGGER update_type_stock_price_ai 
AFTER INSERT ON items 
FOR EACH ROW 
	UPDATE tproducts SET total_buy_price = total_buy_price + new.total_buy_price, total_sell_price = total_sell_price + new.total_sell_price, stock =  stock + new.stock 
	WHERE name = new.type_name;

CREATE TRIGGER update_type_stock_price_bd
BEFORE DELETE ON items
FOR EACH ROW
	UPDATE tproducts SET total_buy_price = total_buy_price - old.total_buy_price, total_sell_price = total_sell_price - old.total_sell_price, stock = stock - old.stock
	WHERE name = old.type_name;

CREATE TRIGGER update_type_stock_price_bu
BEFORE UPDATE ON items
FOR EACH ROW
	UPDATE tproducts SET total_buy_price = total_buy_price - old.total_buy_price + new.total_buy_price, total_sell_price = total_sell_price - old.total_sell_price + new.total_sell_price, stock = stock - old.stock + new.stock
	WHERE name = old.type_name;

CREATE TRIGGER update_global_bu
BEFORE UPDATE ON tproducts
FOR EACH ROW
	UPDATE global SET global_buy_price = global_buy_price - old.total_buy_price + new.total_buy_price, global_sell_price = global_sell_price - old.total_sell_price + new.total_sell_price, global_stock = global_stock - old.stock + new.stock
	WHERE id=1;

CREATE TRIGGER update_global_bd
BEFORE DELETE ON tproducts
FOR EACH ROW
	UPDATE global SET global_buy_price = global_buy_price - old.total_buy_price, global_sell_price = global_sell_price - old.total_sell_price, global_stock = global_stock - old.stock
	WHERE id=1;

