package comjgabrielfreitas.easygcmdemo.notification;

import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.jgabrielfreitas.easygcm.RegisterTokenIntentService;

import java.io.IOException;

/**
 * Created by JGabrielFreitas on 26/04/16.
 */
public class RegisterTokenService extends RegisterTokenIntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    /**
     * Creates an IntentService.
     *
     * @param senderId Initially this call goes out to the network to retrieve the token, subsequent calls are local.<br>
     *                 R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.<br>
     *                 See https://developers.google.com/cloud-messaging/android/start for details on this file.
     */
    public RegisterTokenService(String senderId) {
        super(senderId);
    }

    @Override
    public void sendRegistrationToServer(String token) {

    }

    @Override
    public void subscribeTopics(String token) {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            try {
                pubSub.subscribe(token, "/topics/" + topic, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRegistrationFailure() {
        Log.d(TAG, "OHHH NOOOO");
    }
}
