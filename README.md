[![](https://jitpack.io/v/jgabrielfreitas/DataControllerDemo.svg)](https://jitpack.io/#jgabrielfreitas/DataControllerDemo) [![](https://img.shields.io/badge/Language%20-Java-4682b4.svg)](https://jitpack.io/#jgabrielfreitas/DataControllerDemo)
![EasyGCM](https://raw.githubusercontent.com/jgabrielfreitas/EasyGCM/master/imgs/EasyGcm-header.png)


# EasyGCM
Easy implementation of Google Cloud Messaging for Android (**API lvl 10+**)

### How to
**Step 1.** Add the JitPack repository to your build file
```gradle
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

**Step 2.** Add the dependency
```gradle
dependencies {
		compile 'com.github.jgabrielfreitas:EasyGCM:1.0.0'
	}
```

### Usage
> **These steps should be followed after the creation of its application in the Google Developer Console and add your google-services.json in your workspace**

* Add google-services in your /build.gradle
```gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.0.0'
        classpath 'com.google.gms:google-services:2.0.0-alpha3'
        . . .
    }
}
```

* Add google-services in your app/build.gradle dependencies
```gradle
apply plugin: 'com.google.gms.google-services'
```

* Create your own *GcmListenerService* and overwrite the method onReceived
> The method **onReceived** will be called when your device receive a notification form GCM

```java
public class PushService extends PushListenerService {

  protected void onReceived(String from, Bundle data) {

        /**
         * Show notification for example
         * */
        String message = data.getString("message");
        ReceivedNotification.notify(getApplicationContext(), message, 0);
    }
}
```

* Create your own *RegisterTokenIntentService* and overwrite his methods
> **IMPORTANT:** By default, onHandleIntent method only register the device token in the Google server

```java
public class RegisterTokenService extends RegisterTokenIntentService {

  public RegisterTokenService() {
          senderId = "YOUR_SENDER_ID"; // getString(R.string.gcm_defaultSenderId);
          DEBUG = true;                // boolean to show or not device token on Console
      }

      @Override
      public void sendRegistrationToServer(String token) {
        /**
         * Method called when the InstanceID was used and has a token generated
         * */
      }

      @Override
      public void subscribeTopics(String token) {
        /**
         * Your topics, for example: /topics/global to send notifications for all users
         * */
      }

      @Override
      public void onRegistrationFailure() {
        /**
         * Called if has any exception on 'onHandleIntent'
         * */
      }

}
```

* Declare services and permission in the `AndroidManifest.xml`

```xml
<!-- IMPORTANT -->
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />

<!-- [START gcm_receiver] -->
<receiver
    android:name="com.google.android.gms.gcm.GcmReceiver"
    android:exported="true"
    android:permission="com.google.android.c2dm.permission.SEND">
    <intent-filter>
        <action android:name="com.google.android.c2dm.intent.RECEIVE" />

        <category android:name="com.flip.android" />
    </intent-filter>
</receiver>
<!-- [END gcm_receiver] -->

<!-- IMPORTANT: your PushListenerService child here -->
<service
    android:name=".notification.PushService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.android.c2dm.intent.RECEIVE" />
    </intent-filter>
</service>

<service
    android:name="com.jgabrielfreitas.easygcm.RefreshPushTokenService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.android.gms.iid.InstanceID" />
    </intent-filter>
</service>

<!-- IMPORTANT: your RegisterTokenIntentService child here -->
<service
    android:name=".notification.RegisterTokenService"
    android:exported="false" />
```

> It is not **necessary** to create your own *InstanceIDListenerService*. But if you want, extends *RefreshPushTokenService*

---

### Testing your notification

Sync Gradle, build, run your application.
Everything fine? So check if it's already working!
You can check whether the notification is functioning well
 running a python script inside this repository:
> First of all: Edit the script with your project info and your token device

```terminal
$ python try_gcm.py
```


### License
```
The MIT License (MIT)

Copyright (c) 2016 JGabrielFreitas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
