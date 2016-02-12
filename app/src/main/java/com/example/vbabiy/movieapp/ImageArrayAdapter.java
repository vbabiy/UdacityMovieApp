package com.example.vbabiy.movieapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by vbabiy on 2/11/16.
 */
public class ImageArrayAdapter extends ArrayAdapter<Movie> {


    private final String TAG = ImageArrayAdapter.class.getSimpleName();
    private final LayoutInflater mInflater;
    private final int mResource;

    public ImageArrayAdapter(Context context, int resource) {
        super(context, resource);
        mInflater = LayoutInflater.from(context);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: " + position);
        View view;
        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }

        Movie movie = getItem(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.movie_image);
        imageView.setContentDescription(getContext().getString(R.string.movie_content_description, movie.getTitle()));
        Log.d(TAG, "getView: " + imageView + " URL:" + movie.getFullImageUri().toString());
        Picasso.with(getContext())
                .load(movie.getFullImageUri())
                .placeholder(android.R.drawable.ic_popup_sync)
                .error(android.R.drawable.ic_notification_clear_all)
                .into(imageView);

        return view;
    }

}
