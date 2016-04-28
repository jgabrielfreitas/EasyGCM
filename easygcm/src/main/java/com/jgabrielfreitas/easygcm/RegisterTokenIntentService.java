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
    protected boolean DEBUG = false;


    public RegisterTokenIntentService() {
        super(TAG);
    }

    /**
     * Register token at Google's servers
     * */
    protected void onHandleIntent(Intent intent) {

        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            if (DEBUG)
                Log.i(TAG, "GCM Registration Token: " + token);

            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);

        } catch (Exception e) {
            if (DEBUG)
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
