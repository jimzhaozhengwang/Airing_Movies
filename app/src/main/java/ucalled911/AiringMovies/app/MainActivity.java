package ucalled911.AiringMovies.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ucalled911.AiringMovies.R;
import ucalled911.AiringMovies.adapter.RecyclerViewAdapter;
import ucalled911.AiringMovies.model.Movie;

public class MainActivity extends AppCompatActivity {

    @Inject
    OkHttpClient mClient;
    @Inject
    Gson mGson;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Movie> mMovieList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle(R.string.title);

        MyApp.getmNetComponent().inject(this);

        mRecyclerView = findViewById(R.id.recyclerView);

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/now_playing?api_key=" + getString(R.string.The_Movie_DB_API_key))
                .build();

        mClient.newCall(request).enqueue(new Callback() {
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
                                mMovieList = Arrays.asList(mGson.fromJson(resultsArray.toString(),
                                        Movie[].class));


                                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

                                mAdapter = new RecyclerViewAdapter(MainActivity.this, mMovieList);
                                mRecyclerView.setAdapter(mAdapter);

                                if (savedInstanceState != null) {
                                    Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(getString(R.string.parcelable_key));
                                    mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mRecyclerView != null && mRecyclerView.getLayoutManager() != null && mRecyclerView.getLayoutManager().onSaveInstanceState() != null) {
            outState.putParcelable(getString(R.string.parcelable_key), mRecyclerView.getLayoutManager().onSaveInstanceState());
        }
    }
}