package com.example.sinemaapp.classes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sinemaapp.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity {
YouTubePlayerView player;
private String id_video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        id_video = getIntent().getStringExtra("id_video");
        player = findViewById(R.id.player);
        player.initialize(getString(R.string.api), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                youTubePlayer.setPlaybackEventListener(playbackEventListener);
                youTubePlayer.setFullscreen(true);
                youTubePlayer.setOnFullscreenListener(onFullscreenListener);
                youTubePlayer.cueVideo(id_video);

            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("load video", String.valueOf(youTubeInitializationResult.isUserRecoverableError()));
            }
        });
    }
    private YouTubePlayer.OnFullscreenListener onFullscreenListener = new YouTubePlayer.OnFullscreenListener() {
        @Override
        public void onFullscreen(boolean b) {
            if(!b){
                finish();
            }
        }
    };
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