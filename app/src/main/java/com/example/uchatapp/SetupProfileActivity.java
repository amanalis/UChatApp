package com.example.uchatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uchatapp.databinding.ActivitySetupProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SetupProfileActivity extends AppCompatActivity {

    ActivitySetupProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading profile....");
        dialog.setCancelable(false);

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        binding.imageView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,45);
        });
        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.nameBox.getText().toString();

                if(name.isEmpty()){
                    binding.nameBox.setError("Please enter your name.");
                    return;
                }

                dialog.show();
                if(selectedImage != null){
                    StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();
                                String uid = auth.getUid();
                                String phone = auth.getCurrentUser().getPhoneNumber();
                                String name1 = binding.nameBox.getText().toString();

                                User user = new User(uid, name1,phone,imageUrl);

                                database.getReference()
                                        .child("users")
                                        .child(uid)
                                        .setValue(user)
                                        .addOnSuccessListener(unused -> {
                                            dialog.dismiss();
                                            Intent intent = new Intent(SetupProfileActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        });
                            });
                        }
                    });
                } else {
                    String uid = auth.getUid();
                    String phone = auth.getCurrentUser().getPhoneNumber();

                    User user = new User(uid, name, phone, "No Image");

                    database.getReference()
                            .child("users")
                            .child(uid)
                            .setValue(user)
                            .addOnSuccessListener(unused -> {
                                dialog.dismiss();
                                Intent intent = new Intent(SetupProfileActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            if (data.getData() != null){
                binding.imageView.setImageURI(data.getData());
                selectedImage = data.getData();
            }
        }
    }
}