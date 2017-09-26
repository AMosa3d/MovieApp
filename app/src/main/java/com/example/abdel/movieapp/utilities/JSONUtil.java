package com.example.abdel.movieapp.utilities;

import com.example.abdel.movieapp.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 9/26/2017.
 */


public final class JSONUtil {

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

            Movie movie = new Movie(id,
                    title,
                    poster,
                    overview,
                    date,
                    rating
            );

            moviesList.add(movie);
        }
        return moviesList;
    }
}
