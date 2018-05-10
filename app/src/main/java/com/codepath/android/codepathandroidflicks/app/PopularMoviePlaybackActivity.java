package com.codepath.android.codepathandroidflicks.app;

import android.os.Bundle;

import com.codepath.android.codepathandroidflicks.R;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PopularMoviePlaybackActivity extends YouTubeBaseActivity {

    private YouTubePlayerView mYouTubePlayerView;

    private void initializeYouTube(final String url){
        final String YOUTUBE_API_KEY = "AIzaSyAhtEqjxokaB9ILyZjOMumKo0MqeJa5ovs";

        mYouTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(url);
                youTubePlayer.setFullscreen(true);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie_playback);
        this.setTitle(R.string.title);

        mYouTubePlayerView = findViewById(R.id.video);

        String url = getIntent().getStringExtra("url");

        initializeYouTube(url);
    }
}
