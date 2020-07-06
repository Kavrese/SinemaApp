package com.example.sinemaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class PageFilm extends AppCompatActivity {
    private String img;
    private String name;
    private String des;
    ImageView img_video;
    FloatingActionButton floatingActionButton;
    TextView name_video, description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_film);
        getIntentData();
        img_video = findViewById(R.id.img_video);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        name_video = findViewById(R.id.name_video);
        description = findViewById(R.id.descriptions);
        Picasso.get()
                .load(img)
                .into(img_video);
        name_video.setText(name);
        description.setText(des);
    }

    private void getIntentData() {
        img = getIntent().getStringExtra("img");
        name = getIntent().getStringExtra("name");
        des = getIntent().getStringExtra("des");
    }
}