package com.example.sinemaapp.classes;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sinemaapp.model.Film;
import com.example.sinemaapp.model.User;
import com.example.sinemaapp.model.Video;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
    boolean result = false;
    int maxResultNow;
    ArrayList<Film> list = new ArrayList<>();
    public FireBaseConnect(){}
    public FireBaseConnect (int maxResult){
        this.maxResultNow = maxResult;
    }
    public void outUser () {
        FirebaseAuth.getInstance().signOut();
    }
}
