package com.example.capture.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.capture.R;
import com.example.capture.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    ActivitySignInBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        auth = FirebaseAuth.getInstance();

        //To request focus to the text box so that the keyboard which is set visible in the manifest file is visible
        binding.phoneNumberTextBox.requestFocus();

        //If the user is already logged in go to Main Activity
        if(auth.getCurrentUser()!=null){
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);
            finish();
        }



        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, OtpVerification.class);
                i.putExtra("phoneNumber",binding.phoneNumberTextBox.getText().toString());
                startActivity(i);


            }
        });





    }
}