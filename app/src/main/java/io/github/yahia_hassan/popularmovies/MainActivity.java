package io.github.yahia_hassan.popularmovies;

import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int LOADER_ID = 45;

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
        Log.d(TAG, "JSON as String: " + data);

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
