package com.example.vbabiy.movieapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    private static final String MOVIE_ARG = "movie_id";

    private Movie mMovie;
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mReleaseYear;
    private TextView mOverview;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param movie Movie object
     * @return A new instance of fragment MovieDetailFragment.
     */
    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_ARG, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(MOVIE_ARG);
        }
    }

    private void populateImage() {
        if (mMovie.getImage() != null) {
            Picasso.with(getContext())
                    .load(mMovie.getFullImageUri())
                    .placeholder(android.R.drawable.ic_popup_sync)
                    .error(android.R.drawable.ic_notification_clear_all)
                    .into(mImageView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        mImageView = (ImageView) view.findViewById(R.id.movie_image);
        mTitle = (TextView) view.findViewById(R.id.movie_title);
        mReleaseYear = (TextView) view.findViewById(R.id.movie_release_year);
        mOverview = (TextView) view.findViewById(R.id.movie_overview);

        mTitle.setText(mMovie.getTitle());
        mReleaseYear.setText(mMovie.getReleaseYear());
        mOverview.setText(mMovie.getOverview());

        populateImage();
        return view;
    }

}
