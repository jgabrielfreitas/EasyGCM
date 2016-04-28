import requests
import json

url = "https://android.googleapis.com/gcm/send"
# PUT YOUR TOKEN API HERE
YOUR_TOKEN = 'AIzaSyDAybrJQrslcepbNL5FGKx1e1ENGMeftYU'
key = "key={0}".format(YOUR_TOKEN)
DEVICE_TOKEN = "cI17lG4Id_c:APA91bHjPzrd4Nyw8pLjGR8bnJMXnPdVWDFv4kuI4vZVlBkBnr7iAnM7ajhj-OtYhXaa8hQGZlVEwHAVbg2daFH-mDZrhNlGANl43rRCv7yGOBLrsQKxyMU3ilv-2sB3mY-0jB5JlSbu"

# notification_object example:
#
# {
#  "data": {
#      "message" : "ITS ALIVE"
#  },
#  "to" : "cI17lG4Id_c:APA91bHjPzrd4Nyw8pLjGR8bnJMXnPdVWDFv4kuI4vZVlBkBnr7iAnM7ajhj-OtYhXaa8hQGZlVEwHAVbg2daFH-mDZrhNlGANl43rRCv7yGOBLrsQKxyMU3ilv-2sB3mY-0jB5JlSbu"
# }
#

notification_object = {}
data = {}
data['message'] = "ITS ALIVE!!!"
notification_object['data'] = data
notification_object['to'] = DEVICE_TOKEN

headers = {
    'authorization': key,
    'content-type': "application/json"
    }

response = requests.request("POST", url, data=json.dumps(notification_object), headers=headers)

print(response.text)
