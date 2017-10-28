package com.example.abdel.movieapp;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abdel.movieapp.Adapters.MovieAdapter;
import com.example.abdel.movieapp.AsyncTasks.MovieAsyncTask;
import com.example.abdel.movieapp.Data.MovieContract;
import com.example.abdel.movieapp.Interfaces.HomeRecyclerViewsInterface;
import com.example.abdel.movieapp.Interfaces.HomeUIManagerInterface;
import com.example.abdel.movieapp.Models.Movie;
import com.example.abdel.movieapp.utilities.CursorUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        HomeRecyclerViewsInterface,HomeUIManagerInterface,LoaderManager.LoaderCallbacks<Cursor> {

    ProgressBar mLoadingProgressBar;
    TextView mErrorMessageDisplayer;
    RecyclerView mMovieRecyclerView;
    MovieAdapter mMovieAdapter;

    ArrayList<Movie> currentMovies;

    String currentSortingMethod = "favorite";
    final String MOVIES_BUNDLE_KEY = "MoviesList";

    final int GRID_NUMBER_OF_COLUMNS = 2;
    final int MOVIE_LOADER_ID = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mErrorMessageDisplayer = (TextView) findViewById(R.id.tv_error_message);
        mMovieRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        mMovieAdapter = new MovieAdapter(this,this);

        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(this,GRID_NUMBER_OF_COLUMNS));

        mMovieRecyclerView.setHasFixedSize(true);

        mMovieRecyclerView.setAdapter(mMovieAdapter);



        if(savedInstanceState != null)
        {
            currentMovies = savedInstanceState.getParcelableArrayList(MOVIES_BUNDLE_KEY);
            mMovieAdapter.setMoviesList(currentMovies);
            return;
        }

        loadMoviesData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        loadMoviesData();
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIES_BUNDLE_KEY,currentMovies);

        super.onSaveInstanceState(outState);
    }

    void loadMoviesData(){

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        currentSortingMethod = mPreferences.getString(getString(R.string.pref_key),getString(R.string.pref_default_value));

        if (!currentSortingMethod.equals("favorite"))
        {
            mMovieRecyclerView.setVisibility(View.INVISIBLE);   //hide the recycler view in case of updating it from sorting method to another
            new MovieAsyncTask(this).execute(currentSortingMethod);
        }
        else
            getSupportLoaderManager().initLoader(MOVIE_LOADER_ID,null,this).forceLoad();

    }

    void showErrorMessage (){
        mMovieRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplayer.setVisibility(View.VISIBLE);
    }

    void showMoviesData (){
        mErrorMessageDisplayer.setVisibility(View.INVISIBLE);
        mMovieRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuItem_settings_activity)
        {
            Intent intent = new Intent(this,SettingActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //*************************************** Handel Interface Section ***********************************************//

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(getString(R.string.extra_movie_id),movie);
        if (currentSortingMethod.equals("favorite"))
            intent.putExtra(getString(R.string.extra_movie_fav_id),1);
        startActivity(intent);
    }


    @Override
    public void showError() {
        showErrorMessage();
    }

    @Override
    public void showData(List<Movie> moviesList) {
        showMoviesData();
        currentMovies = (ArrayList<Movie>) moviesList;
        mMovieAdapter.setMoviesList(moviesList);
    }

    @Override
    public void HandelProgressBar(int visibility) {
        mLoadingProgressBar.setVisibility(visibility);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new android.support.v4.content.AsyncTaskLoader<Cursor>(this) {

            Cursor mMovieCursor = null;

            @Override
            protected void onStartLoading() {
                if (mMovieCursor != null)
                    deliverResult(mMovieCursor);
                else
                    forceLoad();    //in case there is already data loaded
            }

            @Override
            public Cursor loadInBackground() {
              //  try
             //   {
                    return getContentResolver().query(MovieContract.MovieEntry.MOVIE_CONTENT_URI,
                            null,
                            null,
                            null,
                            null
                    );
               // }
               // catch (Exception e)
                //{
                    //Log.e("hobba", "Failed to asynchronously load data.");
                    //e.printStackTrace();
                   // return null;
                //}
            }

            @Override
            public void deliverResult(Cursor data) {
                mMovieCursor = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        currentMovies = CursorUtils.getDataFromCursor(data);
        mMovieAdapter.setMoviesList(currentMovies);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //***************************** LOADER *******************************************//


}
