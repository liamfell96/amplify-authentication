package com.example.amplifyauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;


public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_base);

        AWSMobileClient.getInstance().initialize(getApplicationContext(),
                new Callback<UserStateDetails>() {
                       @Override
                        public void onResult(UserStateDetails userStateDetails)
                       {
                           switch (userStateDetails.getUserState())
                           {
                               case SIGNED_IN:
                                   Log.d("Case check", "In signed in case");
                                   break;
                               case SIGNED_OUT:
                                   showSignIn();
                                   Log.d("Case check", "In signed out case");
                               default:
                                   showSignIn();
                                   Log.i("Authentication State", "in default authentication state");
                           }
                       }

                        @Override
                        public void onError(Exception e)
                           {
                               Log.e("Build Sign In", e.toString());
                           }
                });
    }

    private void showSignIn(){
        try{
            Log.d("showSignIn", "Building sign in UI");
            AWSMobileClient.getInstance().showSignIn(this,
                    SignInUIOptions.builder().nextActivity(MainActivity.class).build());
        }
        catch(Exception e) {
            Log.e("showSignIn", e.toString());

        }
    }

}






