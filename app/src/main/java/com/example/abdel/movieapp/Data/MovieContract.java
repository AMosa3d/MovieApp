package com.example.abdel.movieapp.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by abdel on 10/25/2017.
 */

public class MovieContract {

    public static String MOVIE_CONTENT_AUTH = "com.example.abdel.movieapp";

    public static Uri BASE_MOVIE_CONTENT_URI = Uri.parse("content://" + MOVIE_CONTENT_AUTH);

    public static final class MovieEntry implements BaseColumns
    {
        public static final String MOVIE_TABLE = "movie";


        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_POSTER = "poster";
        public static final String MOVIE_OVERVIEW = "overview";
        public static final String MOVIE_RELEASE_DATE = "release_date";
        public static final String MOVIE_RATING = "rating";

        public static final Uri MOVIE_CONTENT_URI =
                BASE_MOVIE_CONTENT_URI.buildUpon()
                        .appendPath(MOVIE_TABLE).build();

        public static final String MOVIE_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + MOVIE_CONTENT_AUTH + "/" + MOVIE_TABLE;

        public static final String MOVIE_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + MOVIE_CONTENT_AUTH + "/" + MOVIE_TABLE;

        public static Uri buildInsertionUri(long id)
        {
            return ContentUris.withAppendedId(MOVIE_CONTENT_URI,id);
        }
    }
}
