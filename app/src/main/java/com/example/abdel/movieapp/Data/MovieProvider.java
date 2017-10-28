package com.example.abdel.movieapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by abdel on 10/25/2017.
 */

public class MovieProvider extends ContentProvider {

    private static final UriMatcher movieUriMatcher = buildMovieUriMatcher();
    private MovieDBHelper movieDBHelper;

    private static final int MOVIE_ALL = 100;
    private static final int MOVIE_WITH_ID = 200;

    private static UriMatcher buildMovieUriMatcher()
    {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(MovieContract.MOVIE_CONTENT_AUTH, MovieContract.MovieEntry.MOVIE_TABLE,MOVIE_ALL);
        matcher.addURI(MovieContract.MOVIE_CONTENT_AUTH, MovieContract.MovieEntry.MOVIE_TABLE + "/#",MOVIE_ALL);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        movieDBHelper = new MovieDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int matchValue = movieUriMatcher.match(uri);

        //switch also works
        if (matchValue == MOVIE_ALL)
            return MovieContract.MovieEntry.MOVIE_DIR_TYPE;
        else if (matchValue == MOVIE_WITH_ID)
            return MovieContract.MovieEntry.MOVIE_ITEM_TYPE;

        //we can return null also
        throw new UnsupportedOperationException("Wrong Uri : " + uri);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int matchValue = movieUriMatcher.match(uri);

        if (matchValue == MOVIE_ALL)
        {
            //getReadableDatabase() we only want to read data not write
            return movieDBHelper.getReadableDatabase().query(MovieContract.MovieEntry.MOVIE_TABLE,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
        }
        else if (matchValue == MOVIE_WITH_ID)
        {
            selection = MovieContract.MovieEntry.MOVIE_ID + " = ?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            return movieDBHelper.getReadableDatabase().query(MovieContract.MovieEntry.MOVIE_TABLE,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
        }

        throw new UnsupportedOperationException("Wrong Uri : " + uri);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //ex : favourite a movie favourite
        final int matchValue = movieUriMatcher.match(uri);

        if (matchValue == MOVIE_ALL)
        {

            long id =  movieDBHelper.getWritableDatabase()
                    .insert(MovieContract.MovieEntry.MOVIE_TABLE,
                        null,
                        values
                    );

            if (id <= 0)
                throw new SQLException("Cannot Insert in table with Uri : " + uri);

            getContext().getContentResolver().notifyChange(uri, null);
            return MovieContract.MovieEntry.buildInsertionUri(id);
        }

        throw new UnsupportedOperationException("Wrong Uri : " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //ex : unfavourite a movie favourite

        final int matchValue = movieUriMatcher.match(uri);

        if (matchValue == MOVIE_ALL)
        {
            return movieDBHelper.getWritableDatabase()
                    .delete(MovieContract.MovieEntry.MOVIE_TABLE,selection,selectionArgs);
        }
        else if (matchValue == MOVIE_WITH_ID)
        {
            selection = MovieContract.MovieEntry.MOVIE_ID + " = ?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            return movieDBHelper.getWritableDatabase()
                    .delete(MovieContract.MovieEntry.MOVIE_TABLE,selection,selectionArgs);
        }

        throw new UnsupportedOperationException("Wrong Uri : " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //we won't let the user update any movie
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final int matchValue = movieUriMatcher.match(uri);
        final SQLiteDatabase mDatabase = movieDBHelper.getWritableDatabase();

        if (matchValue == MOVIE_ALL) {
            int numOfInserted = 0;

            mDatabase.beginTransaction();

            try {
                for (ContentValues value : values)
                {
                    if (value == null)
                        throw new IllegalArgumentException("Cannot have null value");

                    long id = -1;
                    try {
                        id = mDatabase.insertOrThrow(MovieContract.MovieEntry.MOVIE_TABLE, null, value);
                    }
                    catch (SQLiteConstraintException e)
                    {
                        //value is in DB
                    }
                    if (id == -1)
                        numOfInserted++;
                }
                if (numOfInserted > 0) {
                    mDatabase.setTransactionSuccessful();
                    mDatabase.endTransaction();
                    getContext().getContentResolver().notifyChange(uri,null);
                    return numOfInserted;
                }
            }
            finally {
                mDatabase.endTransaction();
            }
        }


        return super.bulkInsert(uri, values);
    }
}
