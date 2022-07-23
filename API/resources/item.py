from argparse import Action
from flask_restful import Resource
from models.items import Item_M
from hmac import compare_digest

class Items(Resource):

    def post(self, name, item):
        return Item_M.save_to_db(name, item)

    def get(self, name, item):
        data = Item_M.get_item_by_name(name, item)
        if not data:
            return {"message": f"El item {item} no existe en {name}"}, 404
        return data[0]
    
    def delete(self, name, item):
        return Item_M.delete_item_by_name(name , item)
    
    def put(self, name, item):
        return Item_M.update_item_s_p(name, item)
