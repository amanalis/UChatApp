package com.example.uchatapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uchatapp.databinding.ActivityPhoneNumberBinding;

public class PhoneNumberActivity extends AppCompatActivity {

    ActivityPhoneNumberBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.continueBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PhoneNumberActivity.this,OTPActivity.class);
            intent.putExtra("phoneNumber",binding.phoneBox.getText().toString());
            startActivity(intent);
        });
    }
}