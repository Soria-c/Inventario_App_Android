from flask_jwt_extended import create_access_token, create_refresh_token
import mariadb
from flask_restful import reqparse
from hmac import compare_digest

conn_params = {
    'user': 'enki',
    'password': 'asd',
    'host':'localhost',
    'database': 'products_eb'
}
class User:

    def __init__(self, id, username, password):
        self.id = id
        self.username = username
        self.password = password
 
    @classmethod
    def find_by_username(cls, username):
        u = Users_M.get_user_by_name(username)
        if (u):
            return cls(**u)
    
    @classmethod
    def find_by_id(cls, _id):
        u = Users_M.get_user_by_id(_id)
        if (u):
            return cls(**u)

class Users_M:
   
    parser = reqparse.RequestParser()
    parser.add_argument("username", type=str, required=True, help="Campo name requerido")
    parser.add_argument("password", type=str, required=True, help="Campo password requerido")

    @staticmethod
    def register_user():
        conn = mariadb.connect(**conn_params)
        db = conn.cursor()
        query = "INSERT INTO users(username, password) VALUES (?, ?)"
        data = Users_M.parser.parse_args()
        try:
            db.execute(query, (data["username"], data["password"]))
        except Exception as e:
            conn.close()
            return {"message": f"El usurario {data['username']} ya existe {e}"}, 400
        conn.commit()
        conn.close()
        return {"message": f"El usuario {data['username']} ha sido creado"}, 201

    @staticmethod
    def get_user_by_name(user):
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = "SELECT * FROM users WHERE username=?"
        db.execute(query,(user,))
        data = list(db)
        conn.close()
        if data:
            return data[0]

    @staticmethod
    def get_user_by_id(idd):
        conn = mariadb.connect(**conn_params)
        db = conn.cursor(dictionary=True)
        query = "SELECT * FROM users WHERE id=?"
        db.execute(query,(idd,))
        data = list(db)
        conn.close()
        if data:
            return data[0]
 
    @staticmethod
    def login_user():
        data = Users_M.parser.parse_args()
        user = User.find_by_username(data["username"])
        if (user and compare_digest(user.username, data["username"])):
            acces_token = create_access_token(identity=user.id, fresh=True)
            refresh_token = create_refresh_token(user.id)
            return {
                "access_token": acces_token,
                "refresh_token": refresh_token
            }, 200
        return {"message": "Invalid credentials"}, 401
