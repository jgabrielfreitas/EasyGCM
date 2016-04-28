package comjgabrielfreitas.easygcmdemo.notification;

import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.jgabrielfreitas.easygcm.RegisterTokenIntentService;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import comjgabrielfreitas.easygcmdemo.R;

import static comjgabrielfreitas.easygcmdemo.RegisterResult.FAILURE;
import static comjgabrielfreitas.easygcmdemo.RegisterResult.SUCCESS;

/**
 * Created by JGabrielFreitas on 26/04/16.
 */
public class RegisterTokenService extends RegisterTokenIntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegisterTokenService() {
        senderId = "396051914799"; // getString(R.string.gcm_defaultSenderId);
        DEBUG = true; // to show token on console
    }

    @Override
    public void sendRegistrationToServer(String token) {
        // TODO here, you'll register your token
        EventBus.getDefault().post(SUCCESS);
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
        Log.e(TAG, ">>> OHHH NOOOO");
        EventBus.getDefault().post(FAILURE);
    }

}
