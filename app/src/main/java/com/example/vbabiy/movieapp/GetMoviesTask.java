package com.example.vbabiy.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vbabiy on 2/11/16.
 */
public class GetMoviesTask extends AsyncTask<Integer, Void, ArrayList<Movie>> {

    public static final int SORT_MOST_POPULAR = 416;
    public static final int SORT_HIGHEST_RATED = 334;
    public static final int SORT_HIGHEST_GROSSING = 773;

    private final String TAG = GetMoviesTask.class.getSimpleName();
    private final Context mContext;
    private ArrayAdapter<Movie> mAdapter;


    public GetMoviesTask(Context context, ArrayAdapter<Movie> adapter) {
        mAdapter = adapter;
        mContext = context;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Integer... params) {
        ArrayList<Movie> data = new ArrayList<>();
        int sort = params[0];
        try {
            Log.d(TAG, "doInBackground: Kicking off job");
            String response = pullApi(sort);

            if (response != null) {
                JSONObject json = new JSONObject(response);
                JSONArray results = json.getJSONArray("results");

                // Pull all the movies
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    Movie obj = new Movie();
                    obj.setId(movie.getString("id"));
                    obj.setImage(movie.getString("poster_path"));
                    obj.setTitle(movie.getString("title"));
                    obj.setReleaseDate(movie.getString("release_date"));
                    obj.setVoteAverage(movie.getString("vote_average"));
                    obj.setOverview(movie.getString("overview"));
                    data.add(obj);
                }

            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "doInBackground: failed", e);
        }

        return data;
    }

    @Nullable
    private String pullApi(int sort) throws IOException {
        URL url = new URL(apiUrl(sort));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        int status = conn.getResponseCode();
        Log.d(TAG, "pullApi: status code " + status);

        switch (status) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                return sb.toString();
        }

        return null;

    }

    private String apiUrl(int sort) {
        Uri.Builder builder = new Uri.Builder();
        String sortField;

        if (sort == SORT_MOST_POPULAR) {
            sortField = "popularity.desc";
        } else if (sort == SORT_HIGHEST_RATED) {
            sortField = "vote_average.desc";
        } else {
            sortField = "revenue.desc";
        }
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("sort_by", sortField)
                .appendQueryParameter("api_key", mContext.getString(R.string.themoviedb_api_key)); // Using api key that is pulled using gradle and a local keys.properties file

        return builder.build().toString();


    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        Log.d(TAG, "onPostExecute: movies " + movies.toString());
        mAdapter.clear();
        mAdapter.addAll(movies);
    }
}
