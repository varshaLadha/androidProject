package com.example.lcom151_two.firebaseapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        storeRegIdInPref(refreshedToken);

        sendRegistrationToServer(refreshedToken);

        Intent registrationComplete = new Intent("registrationComplete");
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ah_firebase", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
        Log.d(TAG, "SharedPreference token: " + token);
    }
}

//refreshed token: cvqnC5-MBR0:APA91bHlbMVALQQ7cCG9gEeAmJb7ZveJByDjwsSG2xL8mjX4dWvt9MbyAJBpMRbgFoodCQoqAPM-Ds4BS1yBhQdPyHwAsPpzU_81eln-UVNXkLCmw5N-U2Q29dxZdHRpycipwP_2muWK