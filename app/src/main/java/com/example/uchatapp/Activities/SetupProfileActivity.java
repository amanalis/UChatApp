package com.example.uchatapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.uchatapp.Models.User;
import com.example.uchatapp.R;
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
    User existingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading profile....");
        dialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        // ✅ Load existing user data if available
        database.getReference().
                child("users")
                .child(auth.getUid())
                .get().addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()) {
                        existingUser = dataSnapshot.getValue(User.class);

                        binding.nameBox.setText(existingUser.getName());
                        if (existingUser.getProfilePic() != null && !existingUser.getProfilePic().equals("No Image")) {

                            Glide.with(SetupProfileActivity.this)
                                    .load(existingUser.getProfilePic())
                                    .placeholder(R.drawable.avatar)
                                    .into(binding.imageView);
                        } else {
                            binding.imageView.setImageResource(R.drawable.avatar);
                        }
                    }
                });

        // ✅ Image picker
        binding.imageView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 45);
        });
        // ✅ Save or update profile
        binding.continueBtn.setOnClickListener(v -> {
            String name = binding.nameBox.getText().toString();

            if (name.isEmpty()) {
                binding.nameBox.setError("Please enter your name.");
                return;
            }

            dialog.show();
            if (selectedImage != null) {
                StorageReference reference = storage.getReference()
                        .child("Profiles")
                        .child(auth.getUid());

                reference.putFile(selectedImage).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        reference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            String uid = auth.getUid();
                            String phone = auth.getCurrentUser().getPhoneNumber();
                            String name1 = binding.nameBox.getText().toString();

                            User user = new User(uid, name1, phone, imageUrl);

                            database.getReference()
                                    .child("users")
                                    .child(uid)
                                    .setValue(user)
                                    .addOnSuccessListener(unused -> {
                                        dialog.dismiss();
                                        Intent intent = new Intent(SetupProfileActivity.this, MainActivity.class);
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
                            Intent intent = new Intent(SetupProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        });
            }
        });

        boolean isEdit = getIntent().getBooleanExtra("isEdit",false);
        if(isEdit){
            binding.continueBtn.setText("Save Changes");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (data.getData() != null) {
                binding.imageView.setImageURI(data.getData());
                selectedImage = data.getData();
            }
        }
    }
}