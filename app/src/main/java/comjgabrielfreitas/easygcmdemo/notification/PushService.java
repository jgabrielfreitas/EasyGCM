package comjgabrielfreitas.easygcmdemo.notification;

import android.os.Bundle;

import com.jgabrielfreitas.easygcm.PushListenerService;

/**
 * Created by JGabrielFreitas on 26/04/16.
 */
public class PushService extends PushListenerService {

    protected void onReceived(String from, Bundle data) {

        /**
         * Show notification for example
         * */
    }
}
