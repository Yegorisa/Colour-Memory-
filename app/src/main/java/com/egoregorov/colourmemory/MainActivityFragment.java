package com.egoregorov.colourmemory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static final String TAG = "MainActivityFragment";

    View mView;
    GridView mBoard;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        mBoard = mView.findViewById(R.id.board);
        mBoard.setAdapter(new BoardAdapter(getActivity(),mView));
        mBoard.setOnItemClickListener((parent, v, position, id) -> Toast.makeText(getContext(), "" + position,
                Toast.LENGTH_SHORT).show());
        Log.d(TAG, "onCreateView: SIZE IS "+ mView.getHeight());
        return mView;
    }

}
