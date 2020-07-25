package com.example.sinemaapp.classes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinemaapp.R;
import com.example.sinemaapp.model.FullinfoVideo;
import com.example.sinemaapp.model.ModelMain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageFilm extends AppCompatActivity {
    private String img,des,name,id_video;
    ImageView img_video;
    ScrollView scroll;
    FloatingActionButton floatingActionButton;
    TextView name_video, description,view_count,like_count,dislike_count;
    YouTubePlayerView player;
    private String view;
    private String like;
    private String dislike;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_film);
        getIntentData();
        scroll = findViewById(R.id.scroll);
        player = findViewById(R.id.player);
        img_video = findViewById(R.id.img_video);
        like_count = findViewById(R.id.like_count);
        view_count = findViewById(R.id.view_count);
        dislike_count = findViewById(R.id.dislike_count);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        name_video = findViewById(R.id.name_video);
        description = findViewById(R.id.descriptions);
        Picasso.get()
                .load(img)
                .into(img_video);
        name_video.setText(name);
        description.setText(des);
        like_count.setText(like);
        dislike_count.setText(dislike);
        view_count.setText(view);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PageFilm.this,PlayerActivity.class);
                in.putExtra("id_video",id_video);
                startActivity(in);
            }
        });
    }
    private void getIntentData() {
        img = getIntent().getStringExtra("img");
        name = getIntent().getStringExtra("name");
        des = getIntent().getStringExtra("des");
        id_video = getIntent().getStringExtra("id_video");
        like = getIntent().getStringExtra("like");
        dislike = getIntent().getStringExtra("dislike");
        view = getIntent().getStringExtra("view");
    }

}