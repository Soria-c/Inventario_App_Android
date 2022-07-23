import requests
from bs4 import BeautifulSoup



def getUrl(name):
    url = f"https://www.google.com.pe/search?q={name}&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjxztzYwrj4AhX-IbkGHQbHDPcQ_AUoAXoECAMQAw&biw=1920&bih=973"
    response = requests.get(url)
    soup = BeautifulSoup(response.content, "html.parser")
    img = soup.find("img", attrs={"class": "yWs4tf"})
    return img["src"]
    

    

