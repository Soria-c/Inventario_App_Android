from flask_restful import Resource
from models.users import Users_M

class UsersRegister(Resource):

    def post(self):
        return Users_M.register_user()

class UsersLogin(Resource):
    
    def post(self):
        return Users_M.login_user()

