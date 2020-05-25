package com.example.amplifyauthentication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set member variables for buttons
        Button mSignInButton;
        Button mSignUpButton;
        Button mSignOutButton;

        //find and assign buttons to member variables
        mSignInButton = findViewById(R.id.sign_in);
        mSignUpButton = findViewById(R.id.sign_up);
        mSignOutButton = findViewById(R.id.sign_out);

        //set initial status of user
        setInitialStatus();


        //set on click lister for sign in button
        Log.i("Listener", "Set up on click listener");
        mSignInButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //log click
                Log.i("Clicked button", "Clicked sign in button");

                //build intent to go to sign in activity
                Intent intent = new Intent(v.getContext(),SignIn.class);
                startActivity(intent);

            }
        });

        //logic for SignOut button
        mSignOutButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
               Log.v("SignOut Button","Signout button clicked");
               AWSMobileClient.getInstance().signOut();

            }
        });


        //register app to listen for user authentication state
        AWSMobileClient.getInstance().addUserStateListener(
                new UserStateListener() {
                    @Override
                    public void onUserStateChanged(UserStateDetails userStateDetails) {
                        UserState userState = userStateDetails.getUserState();

                        switch (userState)
                        {
                            //case the user pressed the signout button
                            case SIGNED_OUT:
                                Log.i("AuthQuickStart", "user is signed out");
                                setSignStatus(userState);
                                break;

                            //case user session expired
                            case SIGNED_OUT_USER_POOLS_TOKENS_INVALID:
                                Log.i("AuthQuickStart", "need to login again.");
                                //Logic here for signing the user back in...
                                setSignStatus(userState);
                                break;

                             //default case
                            default:
                                Log.i("AuthQuickStart", "unsupported");
                                //set status text view to logged out using UI thread
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TextView textView = (TextView) findViewById(R.id.user_status);
                                        textView.setText("Logged OUT");
                                    }
                                });
                        }
                    }
                }
        );
    }


    //conv function to update user authentication status
    private void setSignStatus(UserState userState){
        //set status text view to logged out using UI thread
        switch(userState)
        {

            case SIGNED_OUT:
            case SIGNED_OUT_USER_POOLS_TOKENS_INVALID:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.user_status);
                        textView.setText("Logged OUT");
                    }
                });
                break;

                case SIGNED_IN:
                //set status text view to logged in using UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.user_status);
                        textView.setText("Logged IN");
                    }
                });
                break;
        }
    }

    //conv function to set initial status
    public void setInitialStatus(){

        //set initial state status
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                UserState userState = userStateDetails.getUserState();

                switch (userState) {
                    //call setSignInStatus convienience function
                    case SIGNED_IN:
                    case SIGNED_OUT:
                        setSignStatus(userState);
                        break;
                    //if in another state not signed in or out sign user out
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
