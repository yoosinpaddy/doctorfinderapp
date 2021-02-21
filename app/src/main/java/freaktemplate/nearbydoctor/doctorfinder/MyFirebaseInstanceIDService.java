package freaktemplate.nearbydoctor.doctorfinder;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private static final String TAG = "firebase";
//    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        storeRegIdInPref(s);
        Log.e(TAG, "onNewToken: "+s );
        Log.e(TAG, "onNewToken: "+s );
        Log.e(TAG, "onNewToken: "+s );
        Log.e(TAG, "onNewToken: "+s );
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        Log.e(TAG, "sendRegistrationToServer11: " + token);
        editor.putString("regId", token);
        editor.apply();
    }


    /*@Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        String regId;
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        Log.e(TAG, "sendRegistrationToServer11: " + token);
        editor.putString("regId", token);
        editor.apply();
    }*/
}

