package com.example.abdel.movieapp.utilities;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;


import com.example.abdel.movieapp.BuildConfig;
import com.example.abdel.movieapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by abdel on 9/26/2017.
 */

public final class NetworkUtil {

    public static URL buildURL (String sortMethod)
    {
        final String MOVIE_API_URL = "https://api.themoviedb.org/3/movie/";

        final String API_KEY_PARAM = "api_key";

        final String API = BuildConfig.API_KEY;

        Uri uri = Uri.parse(MOVIE_API_URL).buildUpon()
                .appendPath(sortMethod)
                .appendQueryParameter(API_KEY_PARAM,API)
                .build();

        URL url = null;

        try{
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getDataFromURL (URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");

            if (sc.hasNext())
                return sc.next();
            else
                return null;
        }
        finally {
            urlConnection.disconnect();
        }
    }

    public static Uri buildImageUri (String path,String size)
    {
        final String BASE_PATH = "http://image.tmdb.org/t/p/";

        Uri uri = Uri.parse(BASE_PATH).buildUpon()
                .appendEncodedPath(size)
                .appendEncodedPath(new StringBuilder(path).deleteCharAt(0).toString())
                .build();
        return uri;
    }
}
