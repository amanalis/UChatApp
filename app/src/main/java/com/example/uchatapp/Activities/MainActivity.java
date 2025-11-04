package com.example.uchatapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uchatapp.Adapters.TopStatusAdapter;
import com.example.uchatapp.Models.Status;
import com.example.uchatapp.Models.UserStatus;
import com.example.uchatapp.R;
import com.example.uchatapp.Models.User;
import com.example.uchatapp.Adapters.UsersAdapter;
import com.example.uchatapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase database;
    ArrayList<User> users;
    UsersAdapter usersAdapter;
    TopStatusAdapter topStatusAdapter;
    ArrayList<UserStatus> userStatuses;
    ProgressDialog progressDialog;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        FirebaseMessaging.getInstance()
                .getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String token) {
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("token",token);
                        database.getReference()
                                .child("users")
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(map);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Image...");
        progressDialog.setCancelable(false);

        users = new ArrayList<>();
        userStatuses = new ArrayList<>();

        //getting user data from db
        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(User.class);
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        usersAdapter = new UsersAdapter(this, users);
        topStatusAdapter = new TopStatusAdapter(this, userStatuses);

        binding.statusList.setAdapter(topStatusAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.statusList.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(usersAdapter);

        //Shimmer Chats
        LinearLayout shimmerChats = binding.shimmerChats;
        for (int i = 0; i < 12; i++) {
            View shimmerItem = getLayoutInflater().inflate(R.layout.demo_layout, shimmerChats, false);
            shimmerChats.addView(shimmerItem);
        }

        binding.shimmer.startShimmer();
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.GONE);

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (!user.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                        users.add(user);
                    }
                }

                //stop Shimmer and show actual list
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.view3.setVisibility(View.VISIBLE);


                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Shimmer Status
        LinearLayout shimmerStatus = binding.shimmerStatus;
        for (int i = 0; i < 8; i++) {
            View shimmerItem = getLayoutInflater().inflate(R.layout.demo_status, shimmerStatus, false);
            shimmerStatus.addView(shimmerItem);
        }

        binding.shimmer2.startShimmer();
        binding.shimmer2.setVisibility(View.VISIBLE);
        binding.statusList.setVisibility(View.GONE);

        database.getReference().child("stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userStatuses.clear();
                    for (DataSnapshot storySnapshot : snapshot.getChildren()) {
                        UserStatus status = new UserStatus();
                        status.setName(storySnapshot.child("name").getValue(String.class));
                        status.setProfileImage(storySnapshot.child("profileImage").getValue(String.class));
                        status.setLastUpdated(storySnapshot.child("lastUpdate").getValue(Long.class));

                        ArrayList<Status> statuses = new ArrayList<>();

                        for (DataSnapshot statusSnapshot : storySnapshot.child("statuses").getChildren()) {
                            Status sampleStatus = statusSnapshot.getValue(Status.class);
                            statuses.add(sampleStatus);
                        }

                        status.setStatuses(statuses);
                        userStatuses.add(status);
                    }

                    //stop Shimmer and show actual list
                    binding.shimmer2.stopShimmer();
                    binding.shimmer2.setVisibility(View.GONE);
                    binding.statusList.setVisibility(View.VISIBLE);
                    binding.view3.setVisibility(View.VISIBLE);

                    topStatusAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.status) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 75);
            }
            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (data.getData() != null) {
                progressDialog.show();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Date date = new Date();
                StorageReference reference = storage.getReference().child("status").child(date.getTime() + "");

                reference.putFile(data.getData()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        reference.getDownloadUrl().addOnSuccessListener(uri -> {
                            UserStatus userStatus = new UserStatus();

                            userStatus.setName(user.getName());
                            userStatus.setProfileImage(user.getProfilePic());
                            userStatus.setLastUpdated(date.getTime());

                            HashMap<String, Object> obj = new HashMap<>();
                            obj.put("name", userStatus.getName());
                            obj.put("profileImage", userStatus.getProfileImage());
                            obj.put("lastUpdate", userStatus.getLastUpdated());

                            String imageUrl = uri.toString();
                            Status status = new Status(imageUrl, userStatus.getLastUpdated());

                            database.getReference()
                                    .child("stories")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .updateChildren(obj);

                            database.getReference()
                                    .child("stories")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .child("statuses")
                                    .push().setValue(status);

                            progressDialog.dismiss();
                        });
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentId = FirebaseAuth.getInstance().getUid();//current userID
        database.getReference().child("presence").child(currentId).setValue("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        String currentId = FirebaseAuth.getInstance().getUid();//current userID
        FirebaseDatabase.getInstance().getReference("presence").child(currentId).setValue("Offline");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.setting) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
            startActivity(intent);
            finishAffinity();
        } else if (id == R.id.groups) {
            startActivity(new Intent(MainActivity.this, GroupChatActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);

        MenuItem profileItem = menu.findItem(R.id.profile);
        View actionView = profileItem.getActionView();
        ImageView profileImage = actionView.findViewById(R.id.profileImage);

        if (user != null && user.getProfilePic() != null) {
            Glide.with(this).load(user.getProfilePic())
                    .placeholder(R.drawable.avatar)
                    .circleCrop()
                    .into(profileImage);
        } else {
            profileImage.setImageResource(R.drawable.avatar);
        }

        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SetupProfileActivity.class);
            intent.putExtra("isEdit", true);
            startActivity(intent);
        });

        return super.onCreateOptionsMenu(menu);
    }
}