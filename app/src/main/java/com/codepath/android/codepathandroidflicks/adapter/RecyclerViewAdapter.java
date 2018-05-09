package com.codepath.android.codepathandroidflicks.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Movie> mDataList;
    private LayoutInflater inflater;
    private Context context;
    public final String BASE_URL = "https://image.tmdb.org/t/p/w500/";

    public RecyclerViewAdapter(Context context, List<Movie> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Movie current = mDataList.get(position);
        holder.title.setText(current.getTitle());
        holder.overview.setText(current.getOverview());

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
                .into(holder.image);

//        holder.image.setImageResource;

//        holder.image.setImageResource(current.getImageID());
//        holder.title.setText(current.getTitle());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.getAdapterPosition() == 0){ // information
//                    ((MainActivity) context).go_to_information();
//
//                    // from http://stackoverflow.com/questions/12142255/call-activity-method-from-adapter
//                    // in the future, see if there are other faster alternatives
//
//                }else if (holder.getAdapterPosition() == 1){ // settings
//                    ((MainActivity) context).go_to_settings();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView overview;

        private ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            overview = itemView.findViewById(R.id.overview);
        }
    }

}
