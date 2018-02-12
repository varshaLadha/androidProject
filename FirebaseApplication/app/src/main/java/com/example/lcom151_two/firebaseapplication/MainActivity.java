package com.example.lcom151_two.firebaseapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    //project id = fir-application-e4462
    private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String id=FirebaseInstanceId.getInstance().getToken();

        onNewIntent(getIntent());

        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals("registrationComplete")) {
                    FirebaseMessaging.getInstance().subscribeToTopic("global");

                    displayFirebaseRegId();

                } else if (intent.getAction().equals("pushNotification")) {
                    // new push notification is received

                    Bundle extras=intent.getExtras();
                    if(extras!=null) {
                        String str = extras.getString("foreground");

                        if(str != null){
                            LayoutInflater inflater=getLayoutInflater();
                            View layout=inflater.inflate(R.layout.toast_layout, (ViewGroup)findViewById(R.id.toast_layout_root));
                            TextView toastText=(TextView)layout.findViewById(R.id.toastTextView);

                            toastText.setText(intent.getStringExtra("message"));
                            toastText.setGravity(Gravity.CENTER);

                            Toast toast=new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0);
                            toast.setView(layout);
                            toast.show();
                            txtMessage.setText(intent.getStringExtra("message"));
                        }
                    }
                    else {

                        String message = intent.getStringExtra("message");

                        Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                        txtMessage.setText(message);
                    }
                }

            }
        };

        displayFirebaseRegId();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ah_firebase", 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("registrationComplete"));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("pushNotification"));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            if(bundle.containsKey("message")){
                txtMessage.setText(bundle.getString("message"));
            }else if(bundle.containsKey("data")){
                txtMessage.setText(bundle.getString("data payload"));
            }

        }

    }
}
