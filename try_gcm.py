import requests

url = "https://android.googleapis.com/gcm/send"
# PUT YOUR TOKEN API HERE
YOUR_TOKEN = 'AIzaSyDAybrJQrslcepbNL5FGKx1e1ENGMeftYU'
key = "key={0}".format(YOUR_TOKEN)
DEVICE_TOKEN = "de27nwrGl_g:APA91bHYoev4-3TdTRrelO1pdx7Y-7QNysX2Ebcqq1q8w38lk6Pdtiq5TeVHirEQQgh6wGY61yQs0QdBaJASXrAxBEvMIwWdmUIIZmEayOziKP_fO-S6dmfs3Hn9_ab21BR_2GTSUony"

payload = "{\n    \"data\":{ \n        \"message\": \"ITS ALIVE!!\"\n    },\n    \"to\":\"{0}"\n}".format(DEVICE_TOKEN)
headers = {
    'authorization': key,
    'content-type': "application/json"
    }

response = requests.request("POST", url, data=payload, headers=headers)

print(response.text)
