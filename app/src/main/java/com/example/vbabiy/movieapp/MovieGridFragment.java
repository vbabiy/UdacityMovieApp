package com.example.vbabiy.movieapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


/**

 */
public class MovieGridFragment extends Fragment implements AdapterView.OnItemClickListener {


    private static final String TAG = MovieGridFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private ImageArrayAdapter mAdapter;

    public MovieGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieGridFragment.
     */
    public static MovieGridFragment newInstance() {
        return new MovieGridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: Creating Grid Fragment");
        View view = inflater.inflate(R.layout.fragment_moive_grid, container, false);


        GridView mMovieGrid = (GridView) view.findViewById(R.id.movie_grid);
        mAdapter = new ImageArrayAdapter(getActivity(), R.layout.grid_item);
        mMovieGrid.setAdapter(mAdapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            fetchData();
        } else {
            Toast.makeText(getActivity(), "Can't talk to the internet", Toast.LENGTH_SHORT).show();
        }

        mMovieGrid.setOnItemClickListener(this);
        return view;
    }

    private void fetchData() {
        new GetMoviesTask(getActivity(), mAdapter).execute(Utility.getSortOrder(getActivity()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = mAdapter.getItem(position);

        if (mListener != null) {
            mListener.onItemSelect(movie);
        }
    }

    public void onSortChange() {
        fetchData();
    }


    public interface OnFragmentInteractionListener {
        void onItemSelect(Movie movie);
    }

}
