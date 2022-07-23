from flask_restful import Resource
from models.classes import Classs_M
from flask_jwt_extended import jwt_required

class Products_class(Resource):

    
    def get(self, name):
        data = Classs_M.find_by_name(name)
        if data:
            return data[0], 200
        return {"message": f"La clase {name} no existe"}, 404
    
    def post(self, name):
        return Classs_M.save_to_db(name)
    
    def delete(self, name):
        return Classs_M.delete_class(name)


class Products(Resource):

    #@jwt_required()
    def get(self):
        return Classs_M.retrieve_all()
    
    def delete(self):
        return Classs_M.delete_all()
    
