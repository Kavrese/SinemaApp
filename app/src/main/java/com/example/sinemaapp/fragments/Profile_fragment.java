package com.example.sinemaapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sinemaapp.R;
import com.example.sinemaapp.classes.MainActivity;
import com.example.sinemaapp.classes.StartActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Profile_fragment extends Fragment {
    Button exit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_fragment,container,false);
        exit = view.findViewById(R.id.button_exit);
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
