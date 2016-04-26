package com.jgabrielfreitas.easygcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by JGabrielFreitas on 26/04/16.
 */
public abstract class PushListenerService extends GcmListenerService {

    public void onMessageReceived(String from, Bundle data) {
        onReceived(from, data);
    }

    protected abstract void onReceived(String from, Bundle data);
}
