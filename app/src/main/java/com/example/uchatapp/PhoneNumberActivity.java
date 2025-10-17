package com.example.uchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
            String phoneNumber = binding.phoneBox.getText().toString().trim();
            if(phoneNumber.isEmpty()){
                Toast.makeText(this, "Phone Number cannot be empty!", Toast.LENGTH_SHORT).show();
            }
            else if(!phoneNumber.startsWith("+92")){
                Toast.makeText(this, "Phone number should start with +92", Toast.LENGTH_SHORT).show();
            }
            else if(phoneNumber.length()!= 13){
                Toast.makeText(this, "Invalid phone number. Must be +92 followed by 10 digits", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(PhoneNumberActivity.this,OTPActivity.class);
                intent.putExtra("phoneNumber",binding.phoneBox.getText().toString());
                startActivity(intent);
            }
        });
    }
}