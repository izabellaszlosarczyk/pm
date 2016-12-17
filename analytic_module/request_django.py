import json
import requests

url = "http://127.0.0.1:8000/polls/"
txt = {"sender": "Alice", "receiver": "Bob", "message": "We did it!"}
data = {"sender": "xxx"}
headers = {"Content-type": "application/json", "Accept": "application/json"}
f = open("input_data/data.csv","r+")
# data = f.read()
x = "{\"data\" :\""+  str(data)+"\",\"tekst\":\"" +str(txt)+"\"}"
print x
d = json.loads(x)
r = requests.post(url, data= x,headers=headers)
print r.text