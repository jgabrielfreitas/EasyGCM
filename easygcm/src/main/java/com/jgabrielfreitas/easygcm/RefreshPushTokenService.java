package com.jgabrielfreitas.easygcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by JGabrielFreitas on 26/04/16.
 */
public class RefreshPushTokenService extends InstanceIDListenerService {

    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegisterTokenIntentService.class);
        startService(intent);
    }
}
