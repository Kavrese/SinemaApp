package com.example.sinemaapp;

import android.content.Context;
import android.util.Log;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseConnect {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    int count_video;
    int no;
    int maxResultNow;
    ArrayList<Film> list = new ArrayList<>();
    public FireBaseConnect(){}
    public FireBaseConnect (int maxResult){
        this.maxResultNow = maxResult;
    }
    public void setNewUser (User user){
        addValue("user",null,user);
    }

    public void setNewVideo (Video video){
        addValue("video",video,null);
    }
    private void addValue (final String type, final Video video, final User user){
        if(type.equals("video")){
             final DatabaseReference myRef = db.getReference("download_video").child("count_download");
             myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     final DatabaseReference db_ref = db.getReference("download_video").child("items").child(video.getChannel_name());
                                     db_ref.child(video.getId_video()).setValue(video, new DatabaseReference.CompletionListener() {
                                         @Override
                                         public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                         }
                                     });
                                 }
                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

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
    public int getChildrenCountChannel (String channel){
        DatabaseReference databaseReference = db.getReference().child("download_video").child("items").child(channel);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count_video =(int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return count_video;
    }
    public int editMaxResult (final int maxResultOld){
        final DatabaseReference ref = db.getReference("download_video").child("maxResult");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int maxResultNow = Integer.parseInt(snapshot.getValue().toString());
                no = maxResultNow-maxResultOld;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return no;
    }
    public void setMaxResult (int maxResult){
        DatabaseReference ref = db.getReference("download_video").child("maxResult");
        ref.setValue(maxResult);
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
