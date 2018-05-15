package com.codepath.android.codepathandroidflicks.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.android.codepathandroidflicks.R;

import com.codepath.android.codepathandroidflicks.app.MovieDetailsActivity;
import com.codepath.android.codepathandroidflicks.app.PopularMoviePlaybackActivity;
import com.codepath.android.codepathandroidflicks.model.Movie;
import com.codepath.android.codepathandroidflicks.model.Video;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> mDataList;
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Video> mVideoList;
    private final int STANDARD = 0;
    private final int POPULAR = 1;

    public RecyclerViewAdapter(Context context, List<Movie> data) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == POPULAR) {
            View view = mInflater.inflate(R.layout.recycler_view_item_popular, parent, false);
            return new PopularViewHolder(view);
        }
        View view = mInflater.inflate(R.layout.recycler_view_item_standard, parent, false);
        return new StandardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Movie current = mDataList.get(position);

        if (holder.getItemViewType() == POPULAR) {
            final String BASE_URL = "https://image.tmdb.org/t/p/original/";
            final PopularViewHolder popularViewHolder = (PopularViewHolder) holder;
            String imagePath = BASE_URL + current.getBackdropPath();
            Picasso.get()
                    .load(imagePath)
                    .fit()
                    .placeholder(R.drawable.ic_file_download_black_200dp)
                    .error(R.drawable.ic_error_black_200dp)
                    .into(popularViewHolder.movieImage, new com.squareup.picasso.Callback(){
                        @Override
                        public void onSuccess() {
                            popularViewHolder.playIcon.setVisibility(View.VISIBLE);

                            popularViewHolder.recyclerViewItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder()
                                            .url("https://api.themoviedb.org/3/movie/" + current.getId() + "/videos?api_key=" + mContext.getString(R.string.The_Movie_DB_API_key) + "&language=en-US")
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
                                                try {
                                                    String jsonData = response.body().string();
                                                    JSONObject jsonObject = new JSONObject(jsonData);
                                                    JSONArray resultsArray = jsonObject.getJSONArray(mContext.getString(R.string.results));
                                                    Gson gson = new GsonBuilder().create();
                                                    mVideoList = Arrays.asList(gson.fromJson(resultsArray.toString(),
                                                            Video[].class));

                                                    // find the first valid trailer
                                                    for (int c = 0; c < mVideoList.size(); c++) {
                                                        final Video current = mVideoList.get(c);
                                                        if (mContext.getString(R.string.YouTube).equals(current.getSite()) &&
                                                                mContext.getString(R.string.Trailer).equals(current.getType())) {

                                                            Intent intent = new Intent(mContext, PopularMoviePlaybackActivity.class);
                                                            intent.putExtra(mContext.getString(R.string.url), current.getKey());
                                                            mContext.startActivity(intent);
                                                            break;
                                                        }
                                                    }
                                                } catch (IOException | NullPointerException | JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                        }
                                    });
                                }
                            });
                        }
                        @Override
                        public void onError(Exception e) {

                        }
                    });
        } else {
            final String BASE_URL = "https://image.tmdb.org/t/p/w500/";
            final StandardViewHolder standardViewHolder = (StandardViewHolder) holder;
            standardViewHolder.title.setText(current.getTitle());
            standardViewHolder.overview.setText(current.getOverview());

            String imagePath = "";
            int orientation = mContext.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                imagePath = BASE_URL + current.getPosterPath();
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imagePath = BASE_URL + current.getBackdropPath();
            }

            Picasso.get()
                    .load(imagePath)
                    .placeholder(R.drawable.ic_file_download_black_200dp)
                    .error(R.drawable.ic_error_black_200dp)
                    .into(standardViewHolder.image, new com.squareup.picasso.Callback(){
                        @Override
                        public void onSuccess() {
                            standardViewHolder.recyclerViewItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Gson gson = new Gson();
                                    Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                                    intent.putExtra(mContext.getString(R.string.movieObject), gson.toJson(current));
                                    mContext.startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position).getVoteAverage() >= 7.0) {
            return POPULAR;
        }
        return STANDARD;
    }

    class StandardViewHolder extends RecyclerView.ViewHolder {

        LinearLayout recyclerViewItem;
        ImageView image;
        TextView title;
        TextView overview;

        private StandardViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.standard_image);
            title = itemView.findViewById(R.id.title);
            overview = itemView.findViewById(R.id.overview);
            recyclerViewItem = itemView.findViewById(R.id.recyclerViewItem);
        }
    }

    class PopularViewHolder extends RecyclerView.ViewHolder {

        CoordinatorLayout recyclerViewItem;
        ImageView movieImage;
        ImageView playIcon;

        public PopularViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.popular_image);
            playIcon = itemView.findViewById(R.id.play_icon);
            recyclerViewItem = itemView.findViewById(R.id.recyclerViewItem);
        }
    }

}
