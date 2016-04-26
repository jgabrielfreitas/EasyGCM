package com.jgabrielfreitas.easygcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by JGabrielFreitas on 26/04/16.
 */
public abstract class RegisterTokenIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    protected String senderId;
    /**
     * Creates an IntentService.
     *
     * @param senderId Initially this call goes out to the network to retrieve the token, subsequent calls are local.<br>
     * R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.<br>
     * See https://developers.google.com/cloud-messaging/android/start for details on this file.
     */
    public RegisterTokenIntentService(String senderId) {
        super(TAG);
        this.senderId = senderId;
    }

    /**
     * Register token at Google's servers
     * */
    protected void onHandleIntent(Intent intent) {

        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i(TAG, "GCM Registration Token: " + token);

            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);

        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            onRegistrationFailure();
        }
    }

    /**
     * Method called when the InstanceID was used and has a token generated
     * */
    public abstract void sendRegistrationToServer(String token);

    /**
     * To subscribe your topics
     * */
    public abstract void subscribeTopics(String token);

    /**
     * Called if has any exception on 'onHandleIntent'
     * */
    public abstract void onRegistrationFailure();

}
