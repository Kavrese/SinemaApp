package com.example.sinemaapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sinemaapp.R;
import com.example.sinemaapp.classes.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile_fragment extends Fragment {
    Button exit;
    ImageView avatar;
    TextView name_user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_fragment,container,false);
        name_user = view.findViewById(R.id.name_user);
        exit = view.findViewById(R.id.button_exit);
        avatar = view.findViewById(R.id.avatar);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("items").child(email.substring(0,email.indexOf("@")));
        databaseReference.child("avatar_icon").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get()
                        .load(snapshot.getValue(String.class))
                        .placeholder(R.color.dark)
                        .error(R.color.dark)
                        .into(avatar);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("user_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name_user.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(view.getContext(), StartActivity.class);
                view.getContext().startActivity(in);
            }
        });
        return view;
    }
}
