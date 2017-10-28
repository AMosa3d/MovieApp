package com.example.abdel.movieapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdel on 10/22/2017.
 */

public class Trailer implements Parcelable {
    private String id;

    private String key;

    public Trailer(String id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Trailer (Parcel p)
    {
        id = p.readString();
        key = p.readString();
    }

    public static final Parcelable.Creator<Trailer> TRAILER_CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
    }
}
