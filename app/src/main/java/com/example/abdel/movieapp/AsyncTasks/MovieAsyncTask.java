package com.example.abdel.movieapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.example.abdel.movieapp.Interfaces.HomeUIManagerInterface;
import com.example.abdel.movieapp.Models.Movie;
import com.example.abdel.movieapp.utilities.JSONUtil;
import com.example.abdel.movieapp.utilities.NetworkUtil;

import java.net.URL;
import java.util.List;

/**
 * Created by abdel on 10/22/2017.
 */

public class MovieAsyncTask extends AsyncTask<String,Void,List<Movie>>
{
    private HomeUIManagerInterface mHomeUIManagerInterface;
    public MovieAsyncTask(HomeUIManagerInterface homeUIManagerInterface)
    {
        this.mHomeUIManagerInterface = homeUIManagerInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mHomeUIManagerInterface.HandelProgressBar(View.VISIBLE);
    }

    @Override
    protected List<Movie> doInBackground(String... params) {

        if (params == null)
            return null;

        String searchMethod = params[0];

        URL movieAPIURL = NetworkUtil.buildURL(searchMethod);

        try {
            String movieJSONDataString = NetworkUtil.getDataFromURL(movieAPIURL);

            List<Movie> moviesListFromJSON = JSONUtil.getMoviesFromJSON(movieJSONDataString);

            return moviesListFromJSON;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        mHomeUIManagerInterface.HandelProgressBar(View.INVISIBLE);

        if (movies == null)
            mHomeUIManagerInterface.showError();
        else
        {
            mHomeUIManagerInterface.showData(movies);
        }

    }
}