package comjgabrielfreitas.easygcmdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.jgabrielfreitas.datacontroller.DataController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import comjgabrielfreitas.easygcmdemo.notification.RegisterTokenService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.tokenStatusTextView) TextView tokenStatusTextView;
    private DataController dataController;
    private final String TOKEN_KEY = "TOKEN_REGISTERED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        refreshTextView();
        checkAndStartRegistrationService();
    }

    private void refreshTextView() {

        dataController = new DataController(this);

        if (!dataController.readBooleanData(TOKEN_KEY)) {
            tokenStatusTextView.setText(R.string.registering);
        } else
            tokenStatusTextView.setText(android.R.string.yes);

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 9000).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void checkAndStartRegistrationService() {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegisterTokenService.class);
            startService(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @Subscribe
    public void onRegistrationFinished(RegisterResult result) {

        switch (result) {

            case SUCCESS:
                tokenStatusTextView.setText(R.string.happy_registered);
                dataController.writeData(TOKEN_KEY, true);
                break;
            case FAILURE:
                dataController.writeData(TOKEN_KEY, false);
                tokenStatusTextView.setText(R.string.sad_not_registeres);
                break;
        }
    }
}
