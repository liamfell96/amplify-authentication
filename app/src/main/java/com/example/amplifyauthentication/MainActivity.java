package com.example.amplifyauthentication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //register app to listen for user authentication state
        AWSMobileClient.getInstance().addUserStateListener(
                new UserStateListener() {
                    @Override
                    public void onUserStateChanged(UserStateDetails userStateDetails) {
                        switch (userStateDetails.getUserState())
                        {
                            //case the user pressed the signout button
                            case SIGNED_OUT:
                                Log.i("AuthQuickStart", "user is signed out");
                                break;

                            //case user session expired
                            case SIGNED_OUT_USER_POOLS_TOKENS_INVALID:
                                Log.i("AuthQuickStart", "need to login again.");

                                //Logic here for signing the user back in...
                                break;

                             //default case
                            default:
                                Log.i("AuthQuickStart", "unsupported");
                        }

                    }
                }
        );


        //find current user authentication state and set the text view to that state
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
