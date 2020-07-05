package com.example.sinemaapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseConnect {
    public void setValue(String message,String table,String cell){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(table).child(cell);
        ref.setValue(message); // Value
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
