package com.example.abdel.movieapp.utilities;

import com.example.abdel.movieapp.Models.Movie;
import com.example.abdel.movieapp.Models.Review;
import com.example.abdel.movieapp.Models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 9/26/2017.
 */


public final class JSONUtil {

    public static List<Trailer> getTrailersFromJSON (String JSONString) throws JSONException
    {
        final String TRAILER_LIST = "results";
        final String TRAILER_ID = "id";
        final String TRAILER_KEY = "key";

        JSONObject allData = new JSONObject(JSONString);
        JSONArray list = allData.getJSONArray(TRAILER_LIST);

        List<Trailer> trailersList = new ArrayList<>();

        for (int i=0;i<list.length();i++)
        {
            JSONObject currentObject = list.getJSONObject(i);
            String id = currentObject.getString(TRAILER_ID);
            String key = currentObject.getString(TRAILER_KEY);

            trailersList.add(new Trailer(id,key));
        }

        return trailersList;
    }

    public static List<Review> getReviewsFromJSON (String JSONString) throws JSONException
    {
        final String REVIEW_LIST = "results";
        final String REVIEW_ID = "id";
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";

        JSONObject allData = new JSONObject(JSONString);
        JSONArray list = allData.getJSONArray(REVIEW_LIST);

        List<Review> reviewsList = new ArrayList<>();

        for (int i=0;i<list.length();i++)
        {
            JSONObject currentObject = list.getJSONObject(i);
            String id = currentObject.getString(REVIEW_ID);
            String author = currentObject.getString(REVIEW_AUTHOR);
            String content = currentObject.getString(REVIEW_CONTENT);

            reviewsList.add(new Review(id,author,content));
        }

        return reviewsList;
    }

    public static List<Movie> getMoviesFromJSON (String JSONString) throws JSONException {
        final String MOVIE_LIST = "results";
        final String MOVIE_ID = "id";
        final String MOVIE_TITLE = "title";
        final String MOVIE_POSTER = "poster_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_DATE = "release_date";
        final String MOVE_RATING = "vote_average";


        JSONObject allData = new JSONObject(JSONString);
        JSONArray list = allData.getJSONArray(MOVIE_LIST);

        List<Movie> moviesList = new ArrayList<>();

        for (int i=0;i<list.length();i++)
        {
            JSONObject currentObject = list.getJSONObject(i);
            int id = currentObject.getInt(MOVIE_ID);
            String title = currentObject.getString(MOVIE_TITLE);
            String poster = currentObject.getString(MOVIE_POSTER);
            String overview = currentObject.getString(MOVIE_OVERVIEW);
            String date = currentObject.getString(MOVIE_DATE);
            String rating = currentObject.getString(MOVE_RATING);

            moviesList.add(new Movie(id,
                    title,
                    poster,
                    overview,
                    date,
                    rating
            ));
        }
        return moviesList;
    }
}
