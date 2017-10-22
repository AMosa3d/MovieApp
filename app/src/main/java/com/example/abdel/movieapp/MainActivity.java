package com.example.abdel.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abdel.movieapp.Adapters.MovieAdapter;
import com.example.abdel.movieapp.AsyncTasks.MovieAsyncTask;
import com.example.abdel.movieapp.Interfaces.HomeUIManagerInterface;
import com.example.abdel.movieapp.Interfaces.MovieRecyclerInterface;
import com.example.abdel.movieapp.Models.Movie;


import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieRecyclerInterface,HomeUIManagerInterface {

    ProgressBar mLoadingProgressBar;
    TextView mErrorMessageDisplayer;
    RecyclerView mMovieRecyclerView;
    MovieAdapter mMovieAdapter;

    String currentSortingMethod = "popular";

    final int GRID_NUMBER_OF_COLUMNS = 2;

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

        loadMoviesData();
    }

    void loadMoviesData(){
        mMovieRecyclerView.setVisibility(View.INVISIBLE);   //hide the recycler view in case of updating it from sorting method to another
        new MovieAsyncTask(this).execute(currentSortingMethod);
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

        if (id == R.id.menuItem_sort_method)
        {
            if (currentSortingMethod.equals("popular"))
            {
                currentSortingMethod = "top_rated";  //toggling
                item.setTitle("Popular");
                loadMoviesData();   //rerun the async task to get the new data
            }
            else if (currentSortingMethod.equals("top_rated"))
            {
                currentSortingMethod = "popular";  //toggling
                item.setTitle("Top Rated");
                loadMoviesData();   //rerun the async task to get the new data
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //*************************************** Handel Interface Section ***********************************************//

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(getString(R.string.extra_movie_id),movie);
        startActivity(intent);
    }

    @Override
    public void showError() {
        showErrorMessage();
    }

    @Override
    public void showData(List<Movie> moviesList) {
        showMoviesData();
        mMovieAdapter.setMoviesList(moviesList);
    }

    @Override
    public void HandelProgressBar(int visibility) {
        mLoadingProgressBar.setVisibility(visibility);
    }
}
