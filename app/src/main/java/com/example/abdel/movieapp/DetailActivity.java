package com.example.abdel.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdel.movieapp.Adapters.ReviewsAdapter;
import com.example.abdel.movieapp.Adapters.TrailerAdapter;
import com.example.abdel.movieapp.AsyncTasks.ReviewsAsyncTask;
import com.example.abdel.movieapp.AsyncTasks.TrailerAsyncTask;
import com.example.abdel.movieapp.Data.MovieContract;
import com.example.abdel.movieapp.Interfaces.DetailManagerInterface;
import com.example.abdel.movieapp.LayoutUtils.NonScrollableExpandableListView;
import com.example.abdel.movieapp.LayoutUtils.NonScrollableListView;
import com.example.abdel.movieapp.Models.Movie;
import com.example.abdel.movieapp.Models.Review;
import com.example.abdel.movieapp.Models.Trailer;
import com.example.abdel.movieapp.utilities.NetworkUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailManagerInterface {

    Movie currentMovie;

    final String MOVIE_BUNDLE_KEY = "currentMovieObject";
    final String FAV_BUNDLE_KEY = "currentFavMovieObject";
    int isFav = 0;

    TextView mTitleTextView,mReleaseDateTextView,mOverViewTextView,mRatingTextView,mErrorMessageTextView,mHeadlineTrailer,mHeadlineReview;
    ImageView mPosterImageView,mFavouriteImageView;

    NonScrollableListView mTrailersListView;
    NonScrollableExpandableListView mReviewsListView;
    ProgressBar mLoadingProgressBar;

    TrailerAdapter mTrailerAdapter;
    ReviewsAdapter mReviewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mPosterImageView = (ImageView) findViewById(R.id.im_poster);
        mHeadlineTrailer = (TextView) findViewById(R.id.headline_trailer);
        mHeadlineReview = (TextView) findViewById(R.id.headline_review);
        mRatingTextView = (TextView) findViewById(R.id.tv_rating);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mOverViewTextView = (TextView) findViewById(R.id.tv_overview);
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_detail);
        mTrailersListView = (NonScrollableListView) findViewById(R.id.trailers_list);
        mReviewsListView = (NonScrollableExpandableListView) findViewById(R.id.reviews_list);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mFavouriteImageView = (ImageView) findViewById(R.id.im_favourite);

        mTrailerAdapter = new TrailerAdapter(this,this);
        mReviewsAdapter = new ReviewsAdapter(this,this);

        mTrailersListView.setAdapter(mTrailerAdapter);
        mReviewsListView.setAdapter(mReviewsAdapter);

        if (savedInstanceState != null)
        {
            currentMovie = savedInstanceState.getParcelable(MOVIE_BUNDLE_KEY);
            isFav = savedInstanceState.getInt(FAV_BUNDLE_KEY);
            setDataToLayoutComponents();
            setTrailersAndReviewsData();
            return;
        }

        Intent intent = getIntent();

        if (intent == null)
        {
            currentMovie = null;
            showErrorMessage();
        }
        else
        {
            currentMovie = intent.getParcelableExtra(getString(R.string.extra_movie_id));
            isFav = intent.getIntExtra(getString(R.string.extra_movie_fav_id),0);
            showMovieData();
            setDataToLayoutComponents();
        }

        if (isFav == 0)
            checkDatabase();

        loadTrailersAndReviewsData();

        mFavouriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFav == 0)
                {
                    mFavouriteImageView.setImageResource(R.drawable.yellow_star);
                    favoriteMovie();
                    isFav = 1;
                }
                else
                {
                    mFavouriteImageView.setImageResource(R.drawable.black_star);
                    unFavoriteMovie();
                    isFav = 0;
                }
            }
        });
    }

    void favoriteMovie(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.MOVIE_ID,currentMovie.getId());
        contentValues.put(MovieContract.MovieEntry.MOVIE_TITLE,currentMovie.getTitle());
        contentValues.put(MovieContract.MovieEntry.MOVIE_POSTER,currentMovie.getPoster());
        contentValues.put(MovieContract.MovieEntry.MOVIE_OVERVIEW,currentMovie.getOverview());
        contentValues.put(MovieContract.MovieEntry.MOVIE_RELEASE_DATE,currentMovie.getReleaseDate());
        contentValues.put(MovieContract.MovieEntry.MOVIE_RATING,currentMovie.getRating());

       getContentResolver().insert(MovieContract.MovieEntry.MOVIE_CONTENT_URI,contentValues);

    }

    void unFavoriteMovie(){
        getContentResolver().delete(MovieContract.MovieEntry.MOVIE_CONTENT_URI, MovieContract.MovieEntry.MOVIE_ID + "=" + currentMovie.getId(),null);
    }

    void checkDatabase()
    {
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.MOVIE_CONTENT_URI,null, MovieContract.MovieEntry.MOVIE_ID + "=" + currentMovie.getId(),null,null);

        if (cursor.getCount() == 1)
        {
            mFavouriteImageView.setImageResource(R.drawable.yellow_star);
            isFav = 1;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(MOVIE_BUNDLE_KEY,currentMovie);
        outState.putInt(FAV_BUNDLE_KEY,isFav);
        super.onSaveInstanceState(outState);
    }

    void loadTrailersAndReviewsData()
    {
        String id = "" + currentMovie.getId();
        new TrailerAsyncTask(this).execute(id);
        new ReviewsAsyncTask(this).execute(id);
    }

    void setTrailersAndReviewsData()
    {
        mTrailerAdapter.setTrailerList(currentMovie.getTrailers());
        mReviewsAdapter.setReviewList(currentMovie.getReviews());
    }

    void setDataToLayoutComponents()
    {
        mTitleTextView.setText(currentMovie.getTitle());
        mReleaseDateTextView.setText(currentMovie.getReleaseDate());
        mRatingTextView.setText(currentMovie.getRating());
        mOverViewTextView.setText(currentMovie.getOverview());
        String preferredSize = "w500";
        Uri posterUri = NetworkUtil.buildImageUri(currentMovie.getPoster(), preferredSize);
        Picasso.with(this).load(posterUri).into(mPosterImageView);

        if (isFav == 0)
            mFavouriteImageView.setImageResource(R.drawable.black_star);
        else
            mFavouriteImageView.setImageResource(R.drawable.yellow_star);
    }

    void showErrorMessage()
    {
        mTitleTextView.setVisibility(View.INVISIBLE);
        mPosterImageView.setVisibility(View.INVISIBLE);
        mRatingTextView.setVisibility(View.INVISIBLE);
        mReleaseDateTextView.setVisibility(View.INVISIBLE);
        mOverViewTextView.setVisibility(View.INVISIBLE);
        mTrailersListView.setVisibility(View.INVISIBLE);
        mReviewsListView.setVisibility(View.INVISIBLE);
        mHeadlineTrailer.setVisibility(View.INVISIBLE);
        mHeadlineReview.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    void showMovieData()
    {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mTitleTextView.setVisibility(View.VISIBLE);
        mPosterImageView.setVisibility(View.VISIBLE);
        mRatingTextView.setVisibility(View.VISIBLE);
        mReleaseDateTextView.setVisibility(View.VISIBLE);
        mTrailersListView.setVisibility(View.VISIBLE);
        mReviewsListView.setVisibility(View.VISIBLE);
        mOverViewTextView.setVisibility(View.VISIBLE);
        mHeadlineTrailer.setVisibility(View.VISIBLE);
        mHeadlineReview.setVisibility(View.VISIBLE);
    }


    @Override
    public void showError() {
        showErrorMessage();
    }

    @Override
    public void setTrailersList(List<Trailer> trailersList) {
        showMovieData();
        currentMovie.setTrailers(trailersList);
        mTrailerAdapter.setTrailerList(trailersList);
    }

    @Override
    public void setReviewsList(List<Review> reviewsList) {
        showMovieData();
        currentMovie.setReviews(reviewsList);
        mReviewsAdapter.setReviewList(reviewsList);
    }

    @Override
    public void HandelProgressBar(int visibility) {
        mLoadingProgressBar.setVisibility(visibility);
    }

    @Override
    public void onTrailerClick(Trailer trailer) {
        Uri youtubeLinkUri = Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey());
        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, youtubeLinkUri);
        startActivity(youtubeIntent);
    }

}
