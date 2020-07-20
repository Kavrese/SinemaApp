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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class PageFilm extends YouTubeBaseActivity {
    private String img,des,name,id_video;
    ImageView img_video;
    ScrollView scroll;
    FloatingActionButton floatingActionButton;
    TextView name_video, description;
    YouTubePlayerView player;
    private String YouTube_Api = "AIzaSyB2P4Q7d234l-EI_oO6dAi-BlbMpuOg0CE";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_film);
        getIntentData();
        scroll = findViewById(R.id.scroll);
        player = findViewById(R.id.player);
        img_video = findViewById(R.id.img_video);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        name_video = findViewById(R.id.name_video);
        description = findViewById(R.id.descriptions);
        Picasso.get()
                .load(img)
                .into(img_video);
        name_video.setText(name);
        description.setText(des);
        player.initialize(YouTube_Api, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                youTubePlayer.setPlaybackEventListener(playbackEventListener);
                if (!b) {
                    youTubePlayer.cueVideo(id_video);
                }
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        youTubePlayer.setFullscreen(true);
                        youTubePlayer.play();
                    }
                });
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("load video", String.valueOf(youTubeInitializationResult.isUserRecoverableError()));

            }
        });
    }

    private void getIntentData() {
        img = getIntent().getStringExtra("img");
        name = getIntent().getStringExtra("name");
        des = getIntent().getStringExtra("des");
        id_video = getIntent().getStringExtra("id_video");
    }
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {
        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
}