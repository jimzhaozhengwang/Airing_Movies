package com.codepath.android.codepathandroidflicks.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.codepath.android.codepathandroidflicks.R;

import com.codepath.android.codepathandroidflicks.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> mDataList;
    private LayoutInflater inflater;
    private Context context;
    private final int STANDARD = 0;
    private final int POPULAR = 1;

    public RecyclerViewAdapter(Context context, List<Movie> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == POPULAR){
            View view = inflater.inflate(R.layout.recycler_view_item_popular, parent, false);
            return new PopularViewHolder(view);
        }
        View view = inflater.inflate(R.layout.recycler_view_item_standard, parent, false);
        return new StandardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Movie current = mDataList.get(position);

        if (holder.getItemViewType() == POPULAR){
            final String BASE_URL = "https://image.tmdb.org/t/p/original/";
            PopularViewHolder popularViewHolder = (PopularViewHolder) holder;
            String imagePath = BASE_URL + current.getBackdrop_path();
            Picasso.get()
                    .load(imagePath)
                    .fit()
                    .placeholder(R.drawable.ic_file_download_black_200dp)
                    .error(R.drawable.ic_file_download_black_200dp)
                    .into(popularViewHolder.image);

        }else{
            final String BASE_URL = "https://image.tmdb.org/t/p/w500/";
            StandardViewHolder standardViewHolder = (StandardViewHolder) holder;
            standardViewHolder.title.setText(current.getTitle());
            standardViewHolder.overview.setText(current.getOverview());

            String imagePath = "";
            int orientation = context.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                imagePath = BASE_URL + current.getPoster_path();
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imagePath = BASE_URL + current.getBackdrop_path();
            }

            Picasso.get()
                    .load(imagePath)
                    .placeholder(R.drawable.ic_file_download_black_200dp)
                    .error(R.drawable.ic_file_download_black_200dp)
                    .into(standardViewHolder.image);
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position).getVote_average() >= 7.0){
            return POPULAR;
        }
        return STANDARD;
    }

    class StandardViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView overview;

        private StandardViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.standard_image);
            title = itemView.findViewById(R.id.title);
            overview = itemView.findViewById(R.id.overview);
        }
    }

    class PopularViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public PopularViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.popular_image);
        }
    }

}
