package ucalled911.AiringMovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import ucalled911.AiringMovies.R;
import ucalled911.AiringMovies.app.MovieDetailsActivity;
import ucalled911.AiringMovies.model.Movie;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int OVERVIEW_HEIGHT = 500; // image height is 750 subtract height of 250 to account for title
    private static final int OVERVIEW_TEXT_SIZE = 14; // 14sp

    private int mOverviewLines;
    private List<Movie> mDataList;
    private LayoutInflater mInflater;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Movie> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataList = data;
        mOverviewLines = OVERVIEW_HEIGHT / spToPx(OVERVIEW_TEXT_SIZE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Movie current = mDataList.get(position);

        final String BASE_URL = "https://image.tmdb.org/t/p/w500/";
        final ViewHolder viewHolder = (ViewHolder) holder;

        String imagePath = BASE_URL + current.getPosterPath();

        Picasso.get()
                .load(imagePath)
                .placeholder(R.drawable.ic_file_download_black_126dp_189dp)
                .error(R.drawable.ic_error_black_126dp_189dp)
                .into(viewHolder.image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.recyclerViewItem.setOnClickListener(new View.OnClickListener() {
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

        viewHolder.title.setText(current.getTitle());
        viewHolder.overview.setMaxLines(mOverviewLines);
        viewHolder.overview.setText(current.getOverview());
    }

    private int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mContext.getResources().getDisplayMetrics());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout recyclerViewItem;
        ImageView image;
        TextView title;
        TextView overview;

        private ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.standard_image);
            title = itemView.findViewById(R.id.title);
            overview = itemView.findViewById(R.id.overview);
            recyclerViewItem = itemView.findViewById(R.id.recyclerViewItem);
        }
    }

}
