package com.codepath.android.codepathandroidflicks.app;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.android.codepathandroidflicks.R;
import com.codepath.android.codepathandroidflicks.model.Movie;
import com.codepath.android.codepathandroidflicks.model.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    private TextView mTitle;
    private TextView mReleaseDate;
    private RatingBar mRatingBar;
    private TextView mOverview;
    private List<Video> mVideoList;
    private YouTubePlayerView mYouTubePlayerView;
    private Movie mMovie;

    private void initializeYouTube(final String url){
        mYouTubePlayerView.initialize(getString(R.string.YouTube_API_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(url);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        this.setTitle(R.string.app_name);
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra(getString(R.string.movieObject));
        mMovie = gson.fromJson(strObj, Movie.class);

        mYouTubePlayerView = findViewById(R.id.video);
        mTitle = findViewById(R.id.title);
        mReleaseDate = findViewById(R.id.releaseDate);
        mRatingBar = findViewById(R.id.rating);
        mOverview = findViewById(R.id.overview);
    }

    @Override
    protected void onResume() {
        super.onResume();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + mMovie.getId() + "/videos?api_key=" + getString(R.string.The_Movie_DB_API_key) + "&language=en-US")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String jsonData = response.body().string();
                                JSONObject jsonObject = new JSONObject(jsonData);
                                JSONArray resultsArray = jsonObject.getJSONArray(getString(R.string.results));
                                Gson gson = new GsonBuilder().create();
                                mVideoList = Arrays.asList(gson.fromJson(resultsArray.toString(),
                                        Video[].class));

                                // find the first valid trailer
                                for (int c = 0; c < mVideoList.size(); c++){
                                    final Video current = mVideoList.get(c);
                                    if (getString(R.string.YouTube).equals(current.getSite()) &&
                                            getString(R.string.Trailer).equals(current.getType())){

                                        initializeYouTube(current.getKey());
                                        break;
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        mTitle.setText(mMovie.getTitle());

        mReleaseDate.setText(mMovie.getReleaseDate());

        // vote_average has a maximum value of 10, the maximum number of stars is 5
        mRatingBar.setRating((int) Math.round(mMovie.getVoteAverage()/2));

        mOverview.setText(mMovie.getOverview());
    }
}
