package com.example.abdel.movieapp.AsyncTasks;

import android.os.AsyncTask;
import android.view.View;

import com.example.abdel.movieapp.Interfaces.DetailManagerInterface;
import com.example.abdel.movieapp.Models.Trailer;
import com.example.abdel.movieapp.utilities.JSONUtil;
import com.example.abdel.movieapp.utilities.NetworkUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by abdel on 10/22/2017.
 */

public class TrailerAsyncTask extends AsyncTask<String,Void,List<Trailer>> {

    DetailManagerInterface mDetailManagerInterface;

    public TrailerAsyncTask(DetailManagerInterface detailManagerInterface)
    {
        mDetailManagerInterface = detailManagerInterface;
    }

    private final String TRAILERS_PARAM = "/videos";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDetailManagerInterface.HandelProgressBar(View.VISIBLE);
    }

    @Override
    protected List<Trailer> doInBackground(String... params) {

        if (params == null)
            return null;

        String id = params[0];

        URL trailerAPIURL = NetworkUtil.buildURL(id + TRAILERS_PARAM);

        try
        {
            String trailerJSONDataString = NetworkUtil.getDataFromURL(trailerAPIURL);

            List<Trailer> trailersListFromJSON = JSONUtil.getTrailersFromJSON(trailerJSONDataString);

            return trailersListFromJSON;
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Trailer> trailers) {
        super.onPostExecute(trailers);
        mDetailManagerInterface.HandelProgressBar(View.INVISIBLE);

        if (trailers == null)
            mDetailManagerInterface.showError();
        else
        {
            mDetailManagerInterface.setTrailersList(trailers);
        }
    }
}
