package com.example.amplifyauthentication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                switch (userStateDetails.getUserState()) {
                    case SIGNED_IN:

                        //set status text view to logged in using UI thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView textView = (TextView) findViewById(R.id.user_status);
                                textView.setText("Logged IN");
                            }
                        });
                        break;
                    case SIGNED_OUT:

                        //set status text view to logged out using UI thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView textView = (TextView) findViewById(R.id.user_status);
                                textView.setText("Logged OUT");
                            }
                        });
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        break;
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("INIT", e.toString());
            }
        });
    }
}
