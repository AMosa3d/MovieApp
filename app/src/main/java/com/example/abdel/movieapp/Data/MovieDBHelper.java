package com.example.abdel.movieapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abdel on 10/25/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movie.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_MOVIE_TABLE_QUERY = "CREATE TABLE " + MovieContract.MovieEntry.MOVIE_TABLE + "(" +
                MovieContract.MovieEntry.MOVIE_ID + " INTEGER PRIMARY KEY, "  +
                MovieContract.MovieEntry.MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.MOVIE_POSTER + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.MOVIE_RATING + " TEXT NOT NULL"
                + ")";

        db.execSQL(CREATE_MOVIE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //works only when the version is changed
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.MOVIE_TABLE);

        onCreate(db);
    }
}
