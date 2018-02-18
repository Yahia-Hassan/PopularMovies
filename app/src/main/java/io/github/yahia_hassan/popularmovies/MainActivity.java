package io.github.yahia_hassan.popularmovies;

import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int LOADER_ID = 45;

    private RecyclerView mRecyclerView;
    private PopularMoviesAdapter mPopularMoviesAdapter;
    private GridLayoutManager mLayoutManager;

    private static final String RESULTS_JSON_ARRAY = "results";
    private static final String MOVIE_ORIGINAL_TITLE_STRING = "original_title";
    private static final String MOVIE_POSTER_PATH_STRING = "poster_path";
    private static final String MOVIE_PLOT_SYNOPSIS_STRING = "overview";
    private static final String MOVIE_USER_RATING_STRING = "vote_average";
    private static final String MOVIE_RELEASE_DATE_STRING = "release_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                String jsonString = null;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://api.themoviedb.org/3/movie/popular?api_key=" + APIKey.APIKey)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    jsonString = response.body().string();
                } catch (IOException e) {
                    Log.e(TAG, "IOException caught at loadInBackground: " + e);
                    e.printStackTrace();
                }
                return jsonString;
            }

            @Override
            protected void onStartLoading() {
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        JSONObject rootJSONObject = createJSONObjectFromString(data);
        ArrayList<Movie> movieArrayList = null;

        if (rootJSONObject != null) {
            movieArrayList = createMovieArrayList(rootJSONObject);
        }


        mRecyclerView = findViewById(R.id.recycler_view);
        mPopularMoviesAdapter = new PopularMoviesAdapter(this, movieArrayList);
        mLayoutManager = new GridLayoutManager(this, 2);
        Log.d(TAG, "Adapter and Layout attached");
        mRecyclerView.setAdapter(mPopularMoviesAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


    /**
     * Creates a Movie ArrayList using a JSONObject.
     * @param rootJSONObject
     * @return
     */
    private static ArrayList<Movie> createMovieArrayList (JSONObject rootJSONObject) {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        Movie movie;

        JSONArray resultsJSONArray = rootJSONObject.optJSONArray(RESULTS_JSON_ARRAY);
        int size = resultsJSONArray.length();
        for (int i = 0; i < size; i++) {
            JSONObject nthJSONObject = resultsJSONArray.optJSONObject(i);
            String movieTitle = nthJSONObject.optString(MOVIE_ORIGINAL_TITLE_STRING);
            String moviePoster = nthJSONObject.optString(MOVIE_POSTER_PATH_STRING);
            String plotSypnosis = nthJSONObject.optString(MOVIE_PLOT_SYNOPSIS_STRING);
            String userRating = nthJSONObject.optString(MOVIE_USER_RATING_STRING);
            String releaseDate = nthJSONObject.optString(MOVIE_RELEASE_DATE_STRING);

            movie = new Movie(movieTitle,
                    moviePoster,
                    plotSypnosis,
                    userRating,
                    releaseDate);

            movieArrayList.add(movie);
        }
        return movieArrayList;
    }

    /**
     * Helper method that creates a new JSONObject from String.
     * @param jsonString
     * @return JSONObject or null if JSONException was thrown.
     */
    private static JSONObject createJSONObjectFromString (String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException caught at createJSONObjectFromString: " + e);
            e.printStackTrace();
        }
        return jsonObject;
    }
}
