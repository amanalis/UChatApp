package com.example.uchatapp.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.uchatapp.Models.User;
import com.example.uchatapp.R;
import com.example.uchatapp.databinding.ActivityInfoBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {

    ActivityInfoBinding binding;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String name = getIntent().getStringExtra("name");
        String profile = getIntent().getStringExtra("image");
        String receiverUid = getIntent().getStringExtra("receiverUid");
        Log.d("InfoActivity",receiverUid);


        binding.nameBox.setText(name);
        Glide.with(this).load(profile).placeholder(R.drawable.avatar).into(binding.profile);

        FirebaseDatabase.getInstance().getReference().child("users").child(receiverUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    user = snapshot.getValue(User.class);
                    if (user != null){
                        binding.phoneNumberBox.setText(user.getPhoneNumber());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.blockTxt.setText("Block "+ name);
        binding.reportTxt.setText("Report "+ name);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}