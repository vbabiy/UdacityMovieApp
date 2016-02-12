package com.example.vbabiy.movieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by vbabiy on 2/11/16.
 */
public class Utility {

    public static int getSortOrder(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String order = prefs.getString(context.getString(R.string.pref_order_key),
                context.getString(R.string.pref_sort_popular));

        if (context.getString(R.string.pref_sort_popular).equals(order)) {
            return GetMoviesTask.SORT_MOST_POPULAR;
        } else if (context.getString(R.string.pref_sort_highest_rated).equals(order)) {
            return GetMoviesTask.SORT_HIGHEST_RATED;
        } else {
            return GetMoviesTask.SORT_HIGHEST_GROSSING;
        }
    }
}
