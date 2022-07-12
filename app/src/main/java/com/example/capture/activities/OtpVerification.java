package com.example.capture.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.capture.R;
import com.example.capture.databinding.ActivityOtpVerificationBinding;
import com.example.capture.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.mukesh.OnOtpCompletionListener;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {

    ActivityOtpVerificationBinding binding;
    ProgressDialog dialog;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String verificationID;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.otpView.requestFocus();
//        getSupportActionBar().hide();

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        binding.otpDText.setText("Enter the OTP sent to "+ phoneNumber);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


//        For sending otp dialog

        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending OTP");
        dialog.setCancelable(false);
        dialog.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(120L, TimeUnit.SECONDS)
                .setActivity(OtpVerification.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }


                    @Override
                    public void onCodeSent(@NonNull String verify_id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verify_id, forceResendingToken);
                        verificationID = verify_id;
                        dialog.dismiss();
                    }
                }).build();


            PhoneAuthProvider.verifyPhoneNumber(options);
            uid = FirebaseAuth.getInstance().getUid();
            User user = new User("", phoneNumber, uid, "");

            binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
                @Override
                public void onOtpCompleted(String otp) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, otp);
                    auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                database.getReference()
                                .child("users")
                                .child(uid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Intent intent = new Intent(OtpVerification.this, MainActivity.class);
                                                startActivity(intent);
                                                finishAffinity();    //Closes all the activities before this activity, finish only closes the just before activity
                                                Toast.makeText(OtpVerification.this, "Phonenumber verified succesfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }else{
                                Toast.makeText(OtpVerification.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });




    }
}