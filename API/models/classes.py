from lib2to3.pytree import type_repr
import mariadb
from models.items import Item_M
from models.scrapper import getUrl
conn_params = {
    'user': '',
    'password': '',
    'host':'localhost',
    'database': 'products_eb'
}

class Classs_M:

    
    @staticmethod
    def find_by_name(name):
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = """SELECT *, ROUND(total_class_price, 2) as total_class_price,
         ROUND(total_class_sell_price, 2) as total_class_sell_price,
         ROUND(total_class_earnings, 2) as total_class_earnings
         FROM classes WHERE name=?"""
        values = (name,)
        db.execute(query, values)
        request = list(db)
        conn.close()
        if (request):
            request[0]["items"] = Item_M.get_items_by_id(int(request[0]['class_id']))
        return request
        
    @staticmethod
    def save_to_db(name):
        
        url = getUrl(name)
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = "INSERT INTO classes(name, img) VALUES(?, ?)"
        try:    
            db.execute(query, (name, url))
        except Exception as e:
            conn.close()
            return {"message": f"La clase {name} ya existe {e}"}, 400
        
        conn.commit()
        conn.close()
        return {"message": f"La clase {name} ha sido creada"}, 201
    
    @staticmethod
    def retrieve_all():
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = "SELECT * FROM classes"
        db.execute(query)
        data = list(db)
        for i in data:
            i["items"] = Item_M.get_items_by_id(i['class_id'])
        query = "SELECT * FROM global"
        db.execute(query)
        data2 = list(db)[0]
        data2["Products"] = data
        return data2, 200

    @staticmethod
    def delete_class(name):
        data = Item_M.check_class_exists(name)
        if not data:
            return {"message": f"La clase {name} no existe"}, 404
        data = data[0]
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = "DELETE FROM items WHERE class_id=?"
        db.execute(query,(data["class_id"],))
        query = "DELETE FROM classes WHERE name=?"
        db.execute(query, (name,))
        query = "SELECT * FROM global"
        db.execute(query)
        global_data = list(db)[0]
        new_global_price = global_data["global_price"] - data["total_class_price"]
        new_global_stock = global_data["global_stock"] - data["total_class_stock"]
        new_global_sell = global_data["global_sell_price"] -  data["total_class_sell_price"]
        conn.commit()
        conn.close()
        Item_M.update_global(new_global_price, new_global_sell, new_global_stock)
        return {"message": f"La clase {name} ha sido elimada junto con todos sus items"}, 200
    
    @staticmethod
    def delete_all():
        conn = mariadb.connect(**conn_params)
        db = conn.cursor()
        query = "DELETE FROM items"
        db.execute(query)
        query = "DELETE FROM classes"
        db.execute(query)
        query = "UPDATE global SET global_price=?, global_stock=?, global_sell_price=?, global_earnings=?"
        db.execute(query,(0.0, 0, 0.0, 0.0))
        conn.commit()
        conn.close()
        return {"message": f"Todo el inventario ha sido eliminado"}
