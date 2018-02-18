package io.github.yahia_hassan.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder> {

    public static final String EXTRA_MESSAGE = "DetailActivity key";

    private ArrayList<Movie> mMovieArrayList;
    private Context mContext;

    public PopularMoviesAdapter (Context context, ArrayList<Movie> movieArrayList) {
        mMovieArrayList = movieArrayList;
        mContext = context;
    }



    @Override
    public PopularMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new PopularMoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PopularMoviesViewHolder holder, int position) {
        Movie movie = mMovieArrayList.get(position);
        String UrlFirstPart = "https://image.tmdb.org/t/p/w500/";

        Picasso.with(mContext)
                .load(UrlFirstPart + movie.getMoviePoster())
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }


    public class PopularMoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        public PopularMoviesViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    int position = getAdapterPosition();
                    Movie clickedMovie = mMovieArrayList.get(position);
                    intent.putExtra(EXTRA_MESSAGE, clickedMovie);
                    mContext.startActivity(intent);
                }
            });

            moviePoster = itemView.findViewById(R.id.movie_poster_main_activity);
        }
    }
}
