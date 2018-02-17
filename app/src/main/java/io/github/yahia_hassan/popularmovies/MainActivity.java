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
        ArrayList<Movie> urlArrayList = new ArrayList<>();
        urlArrayList.add(new Movie("/q0R4crx2SehcEEQEkYObktdeFy.jpg"));
        urlArrayList.add(new Movie("/tWqifoYuwLETmmasnGHO7xBjEtt.jpg"));
        urlArrayList.add(new Movie("/qey0tdcOp9kCDdEZuJ87yE3crSe.jpg"));
        urlArrayList.add(new Movie("/gajva2L0rPYkEWjzgFlBXCAVBE5.jpg"));
        urlArrayList.add(new Movie("/xOfdQHNF9TlrdujyAjiKfUhxSXy.jpg"));
        urlArrayList.add(new Movie("/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg"));
        urlArrayList.add(new Movie("/coss7RgL0NH6g4fC2s5atvf3dFO.jpg"));
        urlArrayList.add(new Movie("/AjHZIkzhPXrRNE4VSLVWx6dirK9.jpg"));
        urlArrayList.add(new Movie("/47pLZ1gr63WaciDfHCpmoiXJlVr.jpg"));
        urlArrayList.add(new Movie("/33IDooA29ctGHNE7gQW6cwbmRP3.jpg"));
        urlArrayList.add(new Movie("/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg"));
        urlArrayList.add(new Movie("/oDL2ryJ0sV2bmjgshVgJb3qzvwp.jpg"));
        urlArrayList.add(new Movie("/5nvP4etJ8ecQv8qZM08dK4BAzdK.jpg"));
        urlArrayList.add(new Movie("/sM33SANp9z6rXW8Itn7NnG1GOEs.jpg"));
        urlArrayList.add(new Movie("/6FxOPJ9Ysilpq0IgkrMJ7PubFhq.jpg"));
        urlArrayList.add(new Movie("/doAzav9kfdtsoSdw1MDFvjKq3J4.jpg"));
        urlArrayList.add(new Movie("/eKi8dIrr8voobbaGzDpe8w0PVbC.jpg"));
        urlArrayList.add(new Movie("/zMZTBbBa9eevwpWoVIVHh4Gm1ij.jpg"));
        urlArrayList.add(new Movie("/9rtrRGeRnL0JKtu9IMBWsmlmmZz.jpg"));
        urlArrayList.add(new Movie("/qmi2dsuoyzZdJ0WFZYQazbX8ILj.jpg"));


        mRecyclerView = findViewById(R.id.recycler_view);
        mPopularMoviesAdapter = new PopularMoviesAdapter(this, urlArrayList);
        mLayoutManager = new GridLayoutManager(this, 3);
        Log.d(TAG, "Adapter and Layout attached");
        mRecyclerView.setAdapter(mPopularMoviesAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);



    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
