package com.codepath.android.codepathandroidflicks.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.android.codepathandroidflicks.R;
import com.codepath.android.codepathandroidflicks.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView mImage;
    private TextView mTitle;
    private TextView mReleaseDate;
    private RatingBar mRatingBar;
    private TextView mOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        this.setTitle(R.string.app_name);
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("movieObject");
        Movie movie = gson.fromJson(strObj, Movie.class);

        mImage = findViewById(R.id.image);
        mTitle = findViewById(R.id.title);
        mReleaseDate = findViewById(R.id.releaseDate);
        mRatingBar = findViewById(R.id.rating);
        mOverview = findViewById(R.id.overview);

        final String BASE_URL = "https://image.tmdb.org/t/p/w500/";
        String imagePath = BASE_URL + movie.getPoster_path();
        Picasso.get()
                .load(imagePath)
                .placeholder(R.drawable.ic_file_download_black_200dp)
                .error(R.drawable.ic_error_black_200dp)
                .into(mImage);

        mTitle.setText(movie.getTitle());

        mReleaseDate.setText(movie.getRelease_date());

        // vote_average has a maximum value of 10, the maximum number of stars is 5
        mRatingBar.setRating((int) Math.round(movie.getVote_average()/2));

        mOverview.setText(movie.getOverview());





    }
}
