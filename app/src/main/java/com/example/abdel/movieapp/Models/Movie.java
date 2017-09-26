package com.example.abdel.movieapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdel on 9/26/2017.
 */

public class Movie implements Parcelable {
    int id;
    String title;
    String poster;
    String overview;
    String releaseDate;
    String rating;

    public Movie(int id, String title, String poster, String overview, String releaseDate, String rating) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public String getPoster() {
        return poster;
    }

    public Movie (Parcel p)
    {
        id = p.readInt();
        title = p.readString();
        poster = p.readString();
        overview = p.readString();
        releaseDate = p.readString();
        rating = p.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
