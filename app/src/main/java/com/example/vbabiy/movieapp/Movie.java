package com.example.vbabiy.movieapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by vbabiy on 2/10/16.
 */
public class Movie implements Parcelable {

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
    private String mImage;
    private String mId;
    private String mTitle;
    private String mReleaseDate;
    private String mVoteAverage;
    private String mOverview;

    protected Movie(Parcel in) {
        mId = in.readString();
        mImage = in.readString();
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mVoteAverage = in.readString();
        mOverview = in.readString();
    }

    public Movie() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mImage);
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mVoteAverage);
        dest.writeString(mOverview);
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public Uri getFullImageUri() {
        Uri.Builder builder = new Uri.Builder();
        String path = getImage();
        String finalPath = path.replace("/", "");
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendPath(finalPath);
        return builder.build();
    }

    @Nullable
    public String getReleaseYear() {
        try {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.US);
            return df.format(input.parse(mReleaseDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
