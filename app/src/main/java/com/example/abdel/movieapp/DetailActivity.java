package com.example.abdel.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdel.movieapp.Models.Movie;
import com.example.abdel.movieapp.utilities.NetworkUtil;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Movie currentMovie;

    TextView mTitleTextView,mReleaseDateTextView,mOverViewTextView,mRatingTextView,mErrorMessageTextView;
    ImageView mPosterImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mPosterImageView = (ImageView) findViewById(R.id.im_poster);
        mRatingTextView = (TextView) findViewById(R.id.tv_rating);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mOverViewTextView = (TextView) findViewById(R.id.tv_overview);
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_detail);

        Intent intent = getIntent();

        if (intent == null)
        {
            currentMovie = null;
            showErrorMessage();
        }
        else
        {
            currentMovie = intent.getParcelableExtra(getString(R.string.extra_movie_id));
            showMovieData();
            setDataToLayoutComponents();
        }
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
    }

    void showErrorMessage()
    {
        mTitleTextView.setVisibility(View.INVISIBLE);
        mPosterImageView.setVisibility(View.INVISIBLE);
        mRatingTextView.setVisibility(View.INVISIBLE);
        mReleaseDateTextView.setVisibility(View.INVISIBLE);
        mOverViewTextView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    void showMovieData()
    {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mTitleTextView.setVisibility(View.VISIBLE);
        mPosterImageView.setVisibility(View.VISIBLE);
        mRatingTextView.setVisibility(View.VISIBLE);
        mReleaseDateTextView.setVisibility(View.VISIBLE);
        mOverViewTextView.setVisibility(View.VISIBLE);
    }
}
