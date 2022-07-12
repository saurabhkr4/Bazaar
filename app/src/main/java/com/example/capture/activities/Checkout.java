package com.example.capture.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.capture.R;
import com.example.capture.databinding.ActivityCheckoutBinding;

public class Checkout extends AppCompatActivity {

    ActivityCheckoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}