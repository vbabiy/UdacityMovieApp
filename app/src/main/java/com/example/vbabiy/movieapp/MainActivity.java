package com.example.vbabiy.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MovieGridFragment.OnFragmentInteractionListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private int mSortOrder = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: check sort");
        int sort = Utility.getSortOrder(MainActivity.this);
        Log.d(TAG, "onResume: sort: " + sort + " mSort: " + mSortOrder);
        if (mSortOrder != sort) {
            MovieGridFragment frag = (MovieGridFragment) getSupportFragmentManager().findFragmentById(R.id.movie_grid_fragment);
            frag.onSortChange();
            mSortOrder = sort;
        }
    }

    @Override
    public void onItemSelect(Movie movie) {
        Intent intent = new Intent(this, MovieDetail.class);
        intent.putExtra(MovieDetail.MOVIE_KEY, movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
