package io.github.yahia_hassan.popularmovies;

/**
 * This class represents a movie
 */

public class Movie {
    private String mMovieTitle;
    private String mMoviePoster;
    private String mPlotSynopsis;
    private String mUserRating;
    private String mReleaseDate;

    public Movie(String movieTitle, String moviePoster, String plotSynopsis, String userRating, String releaseDate) {
        mMovieTitle = movieTitle;
        mMoviePoster = moviePoster;
        mPlotSynopsis = plotSynopsis;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
    }
}
