package com.example.amplifyauthentication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_base);


        //create member varibles to store user input
        final EditText mFirstName;
        final EditText mLastName;
        final EditText mEmail;
        final EditText mDateofBirth;
        final EditText mPhoneNumber;
        final EditText mUserName;

        //find edit views
        mFirstName = findViewById(R.id.first_name);
        mLastName = findViewById(R.id.last_name);
        mEmail = findViewById(R.id.email_address);
        mDateofBirth = findViewById(R.id.date_of_birth);
        mPhoneNumber = findViewById(R.id.mobile_number);
        mUserName = findViewById(R.id.user_name);


        //find button
        final Button button = findViewById(R.id.submit_button);

        //setup on click listener
        button.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {


                        //get strings from EditText Views
                        String firstNameString = mFirstName.getText().toString();
                        String lastNameString = mLastName.getText().toString();
                        String emailString = mEmail.getText().toString();
                        String phoneString = mPhoneNumber.getText().toString();
                        String userNameString = mUserName.getText().toString();

                    }


                });
    }
}