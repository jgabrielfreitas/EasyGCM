import requests

url = "https://android.googleapis.com/gcm/send"
# PUT YOUR TOKEN API HERE
YOUR_TOKEN = 'AIzaSyBqKlGfnQ6HdAu1ORt3P-cA4RnA5FK38cw'
key = "key={0}".format(YOUR_TOKEN)

payload = "{\n    \"data\":{ \n        \"message\": \"ITS ALIVE!!\"\n    },\n    \"to\":\"/topics/global\"\n}"
headers = {
    'authorization': key,
    'content-type': "application/json"
    }

response = requests.request("POST", url, data=payload, headers=headers)

print(response.text)
