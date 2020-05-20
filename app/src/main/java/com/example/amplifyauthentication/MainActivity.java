package com.example.amplifyauthentication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

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
                Log.i("INIT", "onResult: " + userStateDetails.getUserState());

            }

            @Override
            public void onError(Exception e) {
                Log.e("INIT", "Initialization error.", e);

            }
        });
    }
}
