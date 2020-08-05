package com.example.sinemaapp.classes;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sinemaapp.model.Film;
import com.example.sinemaapp.model.User;
import com.example.sinemaapp.model.Video;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FireBaseConnect {
    int maxResultNow;
    public FireBaseConnect(){}
    public FireBaseConnect (int maxResult){
        this.maxResultNow = maxResult;
    }
    public void setBdUser (String avatar_icon, String email, String password, boolean premium, String user_name,final View view){
        User user = new User(avatar_icon,email,password,premium,user_name);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("items");
        final DatabaseReference databaseReferenceCount = FirebaseDatabase.getInstance().getReference().child("users").child("total_count");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                databaseReferenceCount.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                           int count = Math.toIntExact(snapshot.getValue(Long.class));
                           databaseReferenceCount.setValue(count+1);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child(email.substring(0,email.indexOf("@"))).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Snackbar.make(view,"User in FireBase created", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }
}
