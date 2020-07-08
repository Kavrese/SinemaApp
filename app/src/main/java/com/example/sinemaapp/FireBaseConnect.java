package com.example.sinemaapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBaseConnect {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    public void setNewUser (User user){
        addCount("user",null,user);
    }

    public void setNewVideo (Video video){
        addCount("video",video,null);
    }
    private void addCount (final String type, final Video video, final User user){
        if(type.equals("video")){
            DatabaseReference db_ref1 = db.getReference("download_video").child("items");
             DatabaseReference db_ref = db.getReference("download_video").child("items").child(video.getChannel_name());
                db_ref1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        DatabaseReference myRef = db.getReference("download_video").child("count_download");
                        int count = (int) snapshot.getChildrenCount();
                        myRef.setValue(String.valueOf(count));
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
             db_ref.child(video.getId_video()).setValue(video, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                }
            });
        }else{
            DatabaseReference db_ref = db.getReference("users").child("items");
            DatabaseReference db_ref1 = db.getReference("users");
            db_ref1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    DatabaseReference myRef = db.getReference("users").child("total_count");
                    int count = (int) snapshot.getChildrenCount();
                    if(count != 0)
                    myRef.setValue(String.valueOf(count));
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
            db_ref.child(String.valueOf(user.getUser_id())).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                }
            });
        }
    }
    public void signIn (String email, String password, final Context context){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Toast.makeText(context, "already signed", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "sign in", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, task.getResult().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public void outUser (){
        FirebaseAuth.getInstance().signOut();
    }
    public void createNewUser (String email, String password, final Context context){
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(context, "create", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
