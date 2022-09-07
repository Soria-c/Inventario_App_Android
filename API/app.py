from flask_jwt_extended import JWTManager
from flask import Flask
from flask_restful import Api
from resources.classes import Products_class, Products
from resources.item import Items
from resources.users import UsersRegister, UsersLogin


app = Flask(__name__)
app.secret_key = ""
api = Api(app)

jwt =JWTManager(app)


#@app.after_request
#def after_request(response):
#  response.headers.add('Access-Control-Allow-Origin', 'http://127.0.0.1:5501') #vscode liveserver
 # response.headers.add('Access-Control-Allow-Headers', 'Content-Type,Authorization')
 # response.headers.add('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE')
 # return response

api.add_resource(Products_class, "/products/<string:name>")
api.add_resource(Items, "/products/<string:name>/<string:item>")
api.add_resource(Products, "/products")
api.add_resource(UsersRegister, "/register")
api.add_resource(UsersLogin, "/login")


if __name__ == "__main__":
    app.run(port=5000, debug=True, host="0.0.0.0")



