package com.example.abdel.movieapp.utilities;

import android.database.Cursor;
import android.net.Uri;

import com.example.abdel.movieapp.Data.MovieContract;
import com.example.abdel.movieapp.Models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 10/28/2017.
 */

public final class CursorUtils {

    public static ArrayList<Movie> getDataFromCursor(Cursor data)
    {
        ArrayList<Movie> moviesList = new ArrayList<>();

        int idIndex = data.getColumnIndex(MovieContract.MovieEntry.MOVIE_ID);
        int titleIndex = data.getColumnIndex(MovieContract.MovieEntry.MOVIE_TITLE);
        int posterIndex = data.getColumnIndex(MovieContract.MovieEntry.MOVIE_POSTER);
        int overviewIndex = data.getColumnIndex(MovieContract.MovieEntry.MOVIE_OVERVIEW);
        int releaseDateIndex = data.getColumnIndex(MovieContract.MovieEntry.MOVIE_RELEASE_DATE);
        int ratingIndex = data.getColumnIndex(MovieContract.MovieEntry.MOVIE_RATING);

        data.moveToFirst();
        try
        {
            do
            {
                moviesList.add(
                        new Movie(data.getInt(idIndex),
                                  data.getString(titleIndex),
                                  data.getString(posterIndex),
                                  data.getString(overviewIndex),
                                  data.getString(releaseDateIndex),
                                  data.getString(ratingIndex)
                        )
                );
            } while (data.moveToNext());

        }
        catch (Exception e)
        {
           data.close();
        }

        return moviesList;
    }
}
