package com.example.abdel.movieapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdel on 10/22/2017.
 */

public class Review implements Parcelable {
    private String id;

    private String author;

    private String content;

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
    }

    public Review (Parcel p)
    {
        id = p.readString();
        author = p.readString();
        content = p.readString();
    }

    public static final Parcelable.Creator<Review> REVIEW_CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
