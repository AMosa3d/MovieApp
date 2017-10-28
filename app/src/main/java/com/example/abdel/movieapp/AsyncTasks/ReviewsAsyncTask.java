package com.example.abdel.movieapp.AsyncTasks;

import android.os.AsyncTask;
import android.view.View;

import com.example.abdel.movieapp.Interfaces.DetailManagerInterface;
import com.example.abdel.movieapp.Models.Review;
import com.example.abdel.movieapp.utilities.JSONUtil;
import com.example.abdel.movieapp.utilities.NetworkUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by abdel on 10/22/2017.
 */

public class ReviewsAsyncTask extends AsyncTask<String,Void,List<Review>> {

    DetailManagerInterface mDetailManagerInterface;

    public ReviewsAsyncTask(DetailManagerInterface detailManagerInterface)
    {
        mDetailManagerInterface = detailManagerInterface;
    }

    private final String REVIEWS_PARAM = "/reviews";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDetailManagerInterface.HandelProgressBar(View.VISIBLE);
    }

    @Override
    protected List<Review> doInBackground(String... params) {

        if (params == null)
            return null;

        String id = params[0];

        URL reviewAPIURL = NetworkUtil.buildURL(id + REVIEWS_PARAM);

        try
        {
            String reviewJSONDataString = NetworkUtil.getDataFromURL(reviewAPIURL);

            List<Review> reviewsListFromJSON = JSONUtil.getReviewsFromJSON(reviewJSONDataString);

            return reviewsListFromJSON;
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
        super.onPostExecute(reviews);
        mDetailManagerInterface.HandelProgressBar(View.INVISIBLE);

        if (reviews == null)
            mDetailManagerInterface.showError();
        else
        {
            mDetailManagerInterface.setReviewsList(reviews);
        }
    }
}
