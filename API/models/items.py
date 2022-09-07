import mariadb
from flask_restful import reqparse
from models.scrapper import getUrl
conn_params = {
    'user': '',
    'password': '',
    'host':'localhost',
    'database': 'products_eb'
}

class Item_M:

    parser = reqparse.RequestParser()
    parser.add_argument("price", type=float)
    parser.add_argument("sell_price", type=float)
    parser.add_argument("stock", type=int)

    @staticmethod
    def get_items_by_id(id):
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = """SELECT *, ROUND(price, 2) as price, 
        ROUND(sell_price, 2) as sell_price,
        ROUND(total_price, 2) as total_price,
        ROUND(total_sell_price, 2) as total_sell_price,
        ROUND(unit_earning, 2) as unit_earning,
        ROUND(total_earning, 2) as total_earning
        FROM items WHERE class_id=?"""
        db.execute(query, (id,))
        request = list(db)
        conn.close()
        return request


    @staticmethod
    def get_item_by_name(name, item):
        data = Item_M.check_class_exists(name)
        if not data:
            return {"message": f"La clase {name} no existe"}, 404
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = """SELECT *, ROUND(price, 2) as price, 
        ROUND(sell_price, 2) as sell_price,
        ROUND(total_price, 2) as total_price,
        ROUND(total_sell_price, 2) as total_sell_price,
        ROUND(unit_earning, 2) as unit_earning,
        ROUND(total_earning, 2) as total_earning
        FROM items WHERE name=? AND class_id=?"""
        db.execute(query, (item, data[0]["class_id"]))
        data = list(db)
        conn.close()
        return data
    
    @staticmethod
    def check_class_exists(name):
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = """SELECT *, ROUND(total_class_price, 2) as total_class_price,
        ROUND(total_class_sell_price, 2) as total_class_sell_price,
        ROUND(total_class_earnings, 2) as total_class_earnings
        FROM classes WHERE name=?"""
        db.execute(query, (name,))
        data = list(db)
        conn.close()
        return data
                

    @staticmethod
    def save_to_db(name, item):
        data = Item_M.check_class_exists(name)
        if not data:
            return {"message": f"La clase {name} no existe"}, 404

        url = getUrl(item)
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        data = data[0]
        data2 = Item_M.parser.parse_args()

        price = data2["price"]
        sell_price = data2["sell_price"]
        stock = data2["stock"]
        total_price = price * stock
        total_sell = sell_price * stock
        class_id = data["class_id"]

        stock_sum = data["total_class_stock"] + stock
        price_sum = data["total_class_price"] + total_price
        sell_sum = data["total_class_sell_price"] + total_sell
        
        query = "INSERT INTO items(name, price, sell_price, stock, unit_earning, total_price, total_sell_price, total_earning, class_id, img) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

        values = (item, price, sell_price, stock, sell_price - price, total_price, total_sell, total_sell - total_price, class_id, url)
        try:    
            db.execute(query, values)
        except Exception as e:
            conn.close()
            return {"message": f"El item {item} ya existe, {e.__class__.__name__}: {e}"}, 400

        query = "SELECT * FROM global"
        db.execute(query)
        global_data = list(db)[0]
        new_global_price = global_data["global_price"] + total_price
        new_global_stock = global_data["global_stock"] + stock
        new_global_sell = global_data["global_sell_price"] + total_sell
        conn.commit()
        conn.close()

        Item_M.update_class_p_s_id(class_id, price_sum, stock_sum, sell_sum)
        Item_M.update_global(new_global_price, new_global_sell, new_global_stock)
        return {"message": f"El item {item} ha sido agregado"}, 201
    

    @staticmethod
    def update_global(global_price, global_sell, global_stock):
        conn = mariadb.connect(**conn_params)
        db = conn.cursor()

        query = "UPDATE global SET global_price=?, global_stock=?, global_sell_price=?, global_earnings=?"
        if global_stock <= 0.0:
            global_price = 0.0
            global_stock = 0
            global_sell = 0.0
        values = (global_price, global_stock, global_sell, global_sell - global_price)
        db.execute(query, values)
        conn.commit()
        conn.close()

    @staticmethod
    def update_class_p_s_id(id, price, stock, sell_price):
        conn = mariadb.connect(**conn_params)
        db = conn.cursor()
        query = "UPDATE classes SET total_class_price=?, total_class_stock=?, total_class_sell_price=?, total_class_earnings=? WHERE class_id=?"
        if stock <= 0:
            price = 0.0
            stock = 0
            sell_price = 0.0
        values = (price, stock, sell_price, sell_price - price, id)
        db.execute(query, values)
        conn.commit()
        conn.close()

        
    @staticmethod
    def delete_item_by_name(name, item):
        data = Item_M.check_class_exists(name)
        if not data:
            return {"message": f"La clase {name} no existe"}, 404
        data = data[0]
        data2 = Item_M.get_item_by_name(name, item)
        if not data2:
            return {"message": f"El item {item} no existe en {name}"}, 404
        data2 = data2[0]
        price_sum = data["total_class_price"]
        stock_sum = data["total_class_stock"]
        sell_sum = data["total_class_sell_price"]

        new_price_sum = price_sum - (data2["price"] * data2["stock"])
        new_stock_sum = stock_sum - data2["stock"]
        new_sell_sum = sell_sum - (data2["sell_price"] * data2["stock"])
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = "DELETE FROM items WHERE name=?"
        db.execute(query, (item,))
        
        query = "SELECT * FROM global"
        db.execute(query)
        global_data = list(db)[0]
        new_global_price = global_data["global_price"] - (data2["price"] * data2["stock"])
        new_global_stock = global_data["global_stock"] - data2["stock"]
        new_global_sell = global_data["global_sell_price"] - (data2["sell_price"] * data2["stock"])
        conn.commit()
        conn.close()
        Item_M.update_class_p_s_id(data["class_id"], new_price_sum, new_stock_sum, new_sell_sum)
        Item_M.update_global(new_global_price, new_global_sell, new_global_stock)
        return {"message":f"El {item} ha sido eliminado de {name}"}
    
    @staticmethod
    def update_item_s_p(name, item):
        data = Item_M.check_class_exists(name)
        if not data:
            return {"message": f"La clase {name} no existe"}, 404
        data = data[0]
        data2 = Item_M.get_item_by_name(name, item)
        if not data2:
            return {"message": f"EL item {item} no existe"}, 404
        data2 = data2[0]
        price_sum = data["total_class_price"]
        stock_sum = data["total_class_stock"]
        sell_sum = data["total_class_sell_price"]
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        data3 = Item_M.parser.parse_args()
        query = "UPDATE items SET stock=?, price=?, sell_price=?, unit_earning=?, total_price=?, total_sell_price=?, total_earning=? WHERE name=?"
        prev_price = data2["price"]
        prev_stock = data2["stock"]
        prev_sell = data2["sell_price"]
        
        next_price = data3["price"]
        next_stock = data3["stock"]
        next_sell = data3["sell_price"]

        new_stock_sum = 0
        new_price_sum = 0
        new_sell_sum = 0
        new_stock = prev_stock
        if next_stock is not None:
            new_stock_sum = next_stock - new_stock 
            new_stock = next_stock
            new_price_sum = (next_stock - prev_stock) * prev_price
            new_sell_sum = (next_stock - prev_stock) * prev_sell
                  
        new_price = prev_price
        if next_price is not None:
            new_price = next_price
            new_price_sum = (next_price * new_stock) - (prev_price * prev_stock)
        
        new_sell = prev_sell
        if next_sell is not None:
            new_sell = next_sell
            new_sell_sum = (next_sell * new_stock) - (prev_sell * prev_stock)

        if (new_price < 0 or new_stock < 0 or new_sell < 0) :
            return {"message": "Limite"}, 200
        
        new_total_stock = stock_sum + new_stock_sum
        new_total_price = price_sum + new_price_sum
        new_total_sell = sell_sum + new_sell_sum

        item_total_price = new_price * new_stock
        item_total_sell = new_sell * new_stock
        item_total_earning = item_total_sell - item_total_price

        values = (new_stock, new_price, new_sell, new_sell - new_price, item_total_price, item_total_sell, item_total_earning, item)
        db.execute(query, values)
        query = "SELECT * FROM global"
        db.execute(query)
        global_data = list(db)[0]
        new_global_price = global_data["global_price"] + new_price_sum
        new_global_stock = global_data["global_stock"] + new_stock_sum
        new_global_sell = global_data["global_sell_price"] + new_sell_sum
        conn.commit()
        conn.close()
        Item_M.update_class_p_s_id(data["class_id"], new_total_price, new_total_stock, new_total_sell)
        Item_M.update_global(new_global_price, new_global_sell, new_global_stock)
        return {"message": f"El item {item}ha sido actualizado"}, 200
