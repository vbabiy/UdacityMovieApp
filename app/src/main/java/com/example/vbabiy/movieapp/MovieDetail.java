package com.example.vbabiy.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetail extends AppCompatActivity {

    public static final String MOVIE_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (savedInstanceState == null) {
            MovieDetailFragment frag = MovieDetailFragment.newInstance((Movie) getIntent().getParcelableExtra(MOVIE_KEY));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_fragment, frag)
                    .commit();

        }
    }
}
